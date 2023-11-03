package pl.quiz.up.quiz.battle.engine;


import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.log4j.Log4j2;
import pl.quiz.up.quiz.battle.dto.*;
import pl.quiz.up.quiz.battle.entity.QuizAnswerEntity;
import pl.quiz.up.quiz.battle.entity.QuizQuestionEntity;
import pl.quiz.up.quiz.battle.enums.ServerInfo;
import pl.quiz.up.quiz.battle.repository.QuizQuestionRepository;
import pl.quiz.up.quiz.battle.utils.SocketClientUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static pl.quiz.up.quiz.battle.utils.Constants.*;

@Log4j2
public class Battle implements Runnable {
    private final BattleRoom battleRoom;
    private final QuizQuestionRepository quizQuestionRepository;
    private final Map<SocketIOClient, Integer> scoreResult = new HashMap<>();
    private Thread thread;
    private List<QuestionMessage> quizQuestionMessage;


    public Battle(BattleRoom battleRoom,  QuizQuestionRepository quizQuestionRepository) {
        this.battleRoom = battleRoom;
        this.quizQuestionRepository = quizQuestionRepository;
        scoreResult.put(this.battleRoom.getFirstClient(), 0);
        scoreResult.put(this.battleRoom.getSecondClient(), 0);
    }

    private static Function<QuizAnswerEntity, AnswerDto> mapAnswer() {
        return answer -> AnswerDto.builder()
                .id(answer.getAnswerId())
                .answer(answer.getAnswer())
                .isCorrect(answer.getCorrect())
                .build();
    }

    @Override
    public void run() {
        addUsersToBattle();
        loadQuestions();

        for (QuestionMessage question : this.quizQuestionMessage) {
            battleRoom.getFirstClient().sendEvent(EVENT_BATTLE_MESSAGE, question);
            battleRoom.getSecondClient().sendEvent(EVENT_BATTLE_MESSAGE, question);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            AnswerMessage firstAnswer = battleRoom.getFirstClient().get(ANSWER_STORE_KEY);
            AnswerMessage secondAnswer = battleRoom.getSecondClient().get(ANSWER_STORE_KEY);
            AnswerDto correct = question.getAnswers().stream().filter(AnswerDto::getIsCorrect).findFirst().orElseThrow();
            boolean firstClientCorrect = Boolean.FALSE;
            boolean secondClientCorrect = Boolean.FALSE;

            if (firstAnswer != null && (firstAnswer.getId() == correct.getId())) {
                int score = this.scoreResult.get(battleRoom.getFirstClient());
                score++;
                this.scoreResult.put(battleRoom.getFirstClient(), score);
                firstClientCorrect = Boolean.TRUE;
            }

            if (secondAnswer != null && (secondAnswer.getId() == correct.getId())) {
                int score = this.scoreResult.get(battleRoom.getSecondClient());
                score++;
                this.scoreResult.put(battleRoom.getSecondClient(), score);
                secondClientCorrect = Boolean.TRUE;
            }

            QuestionResult questionResult = resultResponse(battleRoom.getFirstClient(), firstClientCorrect, battleRoom.getSecondClient(), secondClientCorrect);
            battleRoom.getFirstClient().sendEvent(EVENT_BATTLE_MESSAGE, questionResult);
            battleRoom.getSecondClient().sendEvent(EVENT_BATTLE_MESSAGE, questionResult);

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        battleRoom.getFirstClient().disconnect();
        battleRoom.getSecondClient().disconnect();
    }

    private void addUsersToBattle() {
        battleRoom.getFirstClient().leaveRoom(BATTLE_ROOM);
        battleRoom.getSecondClient().leaveRoom(BATTLE_ROOM);
        battleRoom.getFirstClient().joinRoom(battleRoom.getUuid());
        battleRoom.getSecondClient().joinRoom(battleRoom.getUuid());
        battleRoom.getFirstClient().sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.IN_BATTLE_ROOM));
        battleRoom.getSecondClient().sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.IN_BATTLE_ROOM));
    }

    private QuestionResult resultResponse(SocketIOClient firstClient, Boolean firstClientCorrectAnswer, SocketIOClient secondClient, Boolean secondClientCorrectAnswer) {
        QuestionResult.Result firstResult = new QuestionResult.Result(SocketClientUtils.getUserDetails(firstClient).getUserName(), firstClientCorrectAnswer);
        QuestionResult.Result secondResult = new QuestionResult.Result(SocketClientUtils.getUserDetails(secondClient).getUserName(), secondClientCorrectAnswer);
        return new QuestionResult(List.of(firstResult, secondResult));
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, battleRoom.getUuid());
            thread.start();
            log.info("Battle with id: {} started", battleRoom.getUuid());
        }
    }

    private void loadQuestions() {
        this.quizQuestionMessage = new LinkedList<>();
        for (QuizQuestionEntity quizQuestionEntity : quizQuestionRepository.findRandomQuiz()) {
            QuestionMessage message = QuestionMessage.builder()
                    .id(quizQuestionEntity.getQuestionId())
                    .question(quizQuestionEntity.getQuestion())
                    .answers(quizQuestionEntity.getQuizAnswerEntities().stream().map(mapAnswer()).toList())
                    .build();
            this.quizQuestionMessage.add(message);
        }

    }
}
