package pl.quiz.up.quiz.battle.engine;


import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.log4j.Log4j2;
import pl.quiz.up.quiz.battle.dto.client.AnswerMessage;
import pl.quiz.up.quiz.battle.dto.server.*;
import pl.quiz.up.quiz.battle.entity.*;
import pl.quiz.up.quiz.battle.enums.ServerInfo;
import pl.quiz.up.quiz.battle.repository.BattleRepository;
import pl.quiz.up.quiz.battle.repository.BattleTakeAnswerRepository;
import pl.quiz.up.quiz.battle.repository.QuizQuestionRepository;
import pl.quiz.up.quiz.battle.repository.TakeAnswerRepository;
import pl.quiz.up.quiz.battle.utils.SocketClientUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static pl.quiz.up.quiz.battle.utils.Constants.*;

@Log4j2
public class Battle implements Runnable {
    private final BattleRoom battleRoom;
    private final QuizQuestionRepository quizQuestionRepository;
    private final BattleEntity battleEntity;
    private final BattleTakeAnswerRepository battleTakeAnswerRepository;
    private final TakeAnswerRepository takeAnswerRepository;
    private Thread thread;


    public Battle(BattleRoom battleRoom, QuizQuestionRepository quizQuestionRepository, BattleRepository battleRepository, BattleTakeAnswerRepository battleTakeAnswerRepository, TakeAnswerRepository takeAnswerRepository) {
        this.battleRoom = battleRoom;
        this.quizQuestionRepository = quizQuestionRepository;
        this.battleTakeAnswerRepository = battleTakeAnswerRepository;
        this.takeAnswerRepository = takeAnswerRepository;
        this.battleEntity = battleRepository.save(BattleEntity.builder()
                .firstUserId(SocketClientUtils.getUserDetails(battleRoom.getFirstClient()).getId())
                .secondUserId(SocketClientUtils.getUserDetails(battleRoom.getSecondClient()).getId())
                .build());
    }

    private static AnswerMessage getAnswer(SocketIOClient firstClient) {
        AnswerMessage answerMessage = firstClient.get(ANSWER_STORE_KEY);
        if (Objects.nonNull(answerMessage)) {
            firstClient.del(ANSWER_STORE_KEY);
        }
        return answerMessage;
    }

    private static Function<QuizAnswerEntity, AnswerDto> mapAnswer() {
        return answer -> AnswerDto.builder()
                .id(answer.getAnswerId())
                .answer(answer.getAnswer())
                .isCorrect(answer.getCorrect())
                .build();
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, battleRoom.getUuid());
            thread.start();
            log.info("Battle with id: {} started", battleRoom.getUuid());
        }
    }

    @Override
    public void run() {
        addUsersToBattle();
        BattleResultResponse battleResultResponse = new BattleResultResponse();
        SocketIOClient firstClient = battleRoom.getFirstClient();
        SocketIOClient secondClient = battleRoom.getSecondClient();

        List<BattleTakeAnswerEntity> battleTakeAnswerEntities = new LinkedList<>();


        for (QuestionMessage question : loadQuestions()) {
            threadSleep(5_000);
            ServerMessage questionMessage = ServerMessage.of(ServerInfo.QUESTION_MESSAGE, question);
            firstClient.sendEvent(EVENT_BATTLE_MESSAGE, questionMessage);
            secondClient.sendEvent(EVENT_BATTLE_MESSAGE, questionMessage);
            threadSleep(11_000);
            AnswerMessage firstAnswer = getAnswer(firstClient);
            AnswerMessage secondAnswer = getAnswer(secondClient);
            AnswerDto correct = question.getAnswers().stream().filter(AnswerDto::getIsCorrect).findFirst().orElseThrow();

            QuestionResult.Result firstResult = createQuestionResult(question, firstAnswer, correct);
            QuestionResult.Result secondResult = createQuestionResult(question, secondAnswer, correct);

            QuestionResult questionResult = new QuestionResult(Map.of(SocketClientUtils.getUserDetails(firstClient).getUserName(), firstResult,
                    SocketClientUtils.getUserDetails(secondClient).getUserName(), secondResult));
            battleResultResponse.addQuestionResult(questionResult);
            ServerMessage serverMessage = ServerMessage.of(ServerInfo.ANSWER_RESULT, questionResult);
            firstClient.sendEvent(EVENT_BATTLE_MESSAGE, serverMessage);
            secondClient.sendEvent(EVENT_BATTLE_MESSAGE, serverMessage);

            battleTakeAnswerEntities.add(createBattleTakeAnswerEntity(firstResult, firstAnswer, secondResult, secondAnswer));
        }

        battleTakeAnswerRepository.saveAll(battleTakeAnswerEntities);

        ServerMessage serverMessageBattleResult = ServerMessage.of(ServerInfo.BATTLE_RESULT, battleResultResponse);
        firstClient.sendEvent(EVENT_BATTLE_MESSAGE, serverMessageBattleResult);
        secondClient.sendEvent(EVENT_BATTLE_MESSAGE, serverMessageBattleResult);


        firstClient.disconnect();
        secondClient.disconnect();
    }

    private BattleTakeAnswerEntity createBattleTakeAnswerEntity(QuestionResult.Result firstResult, AnswerMessage firstAnswer, QuestionResult.Result secondResult, AnswerMessage secondAnswer) {
        TakeAnswerEntity firstUserTakeAnswerEntity = TakeAnswerEntity.builder()
                .questionId(firstResult.getQuestionId())
                .answerId(firstResult.getAnswerId())
                .openTextAnswer(Objects.nonNull(firstAnswer) ? firstAnswer.getAnswer() : null)
                .mostActualAnswer(Boolean.TRUE)
                .build();

        TakeAnswerEntity secondUserTakeAnswerEntity = TakeAnswerEntity.builder()
                .questionId(secondResult.getQuestionId())
                .answerId(secondResult.getAnswerId())
                .openTextAnswer(Objects.nonNull(secondAnswer) ? secondAnswer.getAnswer() : null)
                .mostActualAnswer(Boolean.TRUE)
                .build();

        return BattleTakeAnswerEntity
                .builder()
                .battleId(battleEntity.getId())
                .firstUserTakeAnswer(takeAnswerRepository.save(firstUserTakeAnswerEntity).getId())
                .secondUserTakeAnswer(takeAnswerRepository.save(secondUserTakeAnswerEntity).getId())
                .build();
    }

    private void threadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private QuestionResult.Result createQuestionResult(QuestionMessage question, AnswerMessage answerMessage, AnswerDto correct) {
        if (answerMessage != null && (answerMessage.getId() == correct.getId())) {
            return new QuestionResult.Result(question.getId(), answerMessage.getId(), Boolean.TRUE);
        } else {
            return new QuestionResult.Result(question.getId(), Objects.nonNull(answerMessage) ? answerMessage.getId() : null, Boolean.FALSE);
        }
    }

    private void addUsersToBattle() {
        battleRoom.getFirstClient().leaveRoom(BATTLE_ROOM);
        battleRoom.getSecondClient().leaveRoom(BATTLE_ROOM);
        battleRoom.getFirstClient().joinRoom(battleRoom.getUuid());
        battleRoom.getSecondClient().joinRoom(battleRoom.getUuid());
        battleRoom.getFirstClient().sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.IN_BATTLE_ROOM));
        battleRoom.getSecondClient().sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.IN_BATTLE_ROOM));
    }

    private List<QuestionMessage> loadQuestions() {
        List<QuestionMessage> quizQuestionMessage = new LinkedList<>();
        for (QuizQuestionEntity quizQuestionEntity : quizQuestionRepository.findRandomQuiz()) {
            QuestionMessage message = QuestionMessage.builder()
                    .id(quizQuestionEntity.getQuestionId())
                    .question(quizQuestionEntity.getQuestion())
                    .answers(quizQuestionEntity.getQuizAnswerEntities().stream().map(mapAnswer()).toList())
                    .build();
            quizQuestionMessage.add(message);
        }
        return quizQuestionMessage;
    }
}
