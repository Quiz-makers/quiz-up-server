package pl.quiz.up.quiz.battle.engine;


import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.log4j.Log4j2;
import pl.quiz.up.quiz.battle.dto.*;
import pl.quiz.up.quiz.battle.entity.QuizAnswerEntity;
import pl.quiz.up.quiz.battle.entity.QuizQuestionEntity;
import pl.quiz.up.quiz.battle.enums.ServerInfo;
import pl.quiz.up.quiz.battle.repository.QuizQuestionRepository;
import pl.quiz.up.quiz.battle.utils.SocketClientUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Battle implements Runnable {
    private Thread thread;
    private final BattleRoom battleRoom;
    private final DataBus dataBus;
    private final QuizQuestionRepository quizQuestionRepository;
    private List<QuestionMessage> quizQuestionMessage;
    private final Map<SocketIOClient, Integer> scoreResult = new HashMap<>();


    public Battle(BattleRoom battleRoom, DataBus dataBus, QuizQuestionRepository quizQuestionRepository) {
        this.battleRoom = battleRoom;
        this.dataBus = dataBus;
        this.quizQuestionRepository = quizQuestionRepository;
        scoreResult.put(this.battleRoom.getFirstClient(), 0);
        scoreResult.put(this.battleRoom.getSecondClient(), 0);
    }

    @Override
    public void run() {
        battleRoom.getFirstClient().joinRoom(battleRoom.getUuid());
        battleRoom.getSecondClient().joinRoom(battleRoom.getUuid());

        battleRoom.getFirstClient().sendEvent("server_message", new ServerMessage(ServerInfo.IN_BATTLE_ROOM, "You have been added to the battle room", new Date()));
        battleRoom.getSecondClient().sendEvent("server_message", new ServerMessage(ServerInfo.IN_BATTLE_ROOM, "You have been added to the battle room", new Date()));
        loadQuestions();

        for (QuestionMessage question : this.quizQuestionMessage) {
            battleRoom.getFirstClient().sendEvent("battle", question);
            battleRoom.getSecondClient().sendEvent("battle", question);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            AnswerMessage firstAnswer = dataBus.getMessage(battleRoom.getFirstClient());
            AnswerMessage secondAnswer = dataBus.getMessage(battleRoom.getSecondClient());
            AnswerDto correct = question.getAnswers().stream().filter(AnswerDto::getIsCorrect).findFirst().get();
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
            battleRoom.getFirstClient().sendEvent("battle", questionResult);
            battleRoom.getSecondClient().sendEvent("battle", questionResult);

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        battleRoom.getFirstClient().disconnect();
        battleRoom.getSecondClient().disconnect();

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
                    .answers(quizQuestionEntity.getQuizAnswerEntities().stream().map(mapAnswer()).collect(Collectors.toList()))
                    .build();
            this.quizQuestionMessage.add(message);
        }

    }

    private static Function<QuizAnswerEntity, AnswerDto> mapAnswer() {
        return answer -> AnswerDto.builder()
                .id(answer.getAnswerId())
                .answer(answer.getAnswer())
                .isCorrect(answer.getCorrect())
                .build();
    }
}
