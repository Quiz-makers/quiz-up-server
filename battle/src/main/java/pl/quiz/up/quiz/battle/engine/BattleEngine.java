package pl.quiz.up.quiz.battle.engine;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.battle.dto.AnswerMessage;
import pl.quiz.up.quiz.battle.dto.BattleRoom;
import pl.quiz.up.quiz.battle.dto.ServerMessage;
import pl.quiz.up.quiz.battle.enums.ServerInfo;
import pl.quiz.up.quiz.battle.repository.QuizQuestionRepository;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import static pl.quiz.up.quiz.battle.utils.Constants.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class BattleEngine {
    private final ConcurrentLinkedQueue<SocketIOClient> waitingRoomQueue = new ConcurrentLinkedQueue<>();
    private final QuizQuestionRepository quizQuestionRepository;

    public void addToWaitingRoom(SocketIOClient client) {
        client.joinRoom(BATTLE_ROOM);
        client.sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.IN_WAITING_ROOM));
        this.waitingRoomQueue.add(client);
    }

    public void leaveWaitingRoom(SocketIOClient client) {
        client.leaveRoom(BATTLE_ROOM);
        this.waitingRoomQueue.remove(client);
    }

    public void addClientAnswer(SocketIOClient client, AnswerMessage message) {
        client.set(ANSWER_STORE_KEY, message);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void startBattle() {
        int pairSize = this.waitingRoomQueue.size() / 2;
        log.info("Started startBattle task, found {} pairs", pairSize);
        for (int i = 0; i < pairSize; i++) {
            Battle battle = new Battle(createBattleRoom(), quizQuestionRepository);
            battle.start();
        }
    }


    private BattleRoom createBattleRoom() {
        SocketIOClient firstClient = this.waitingRoomQueue.poll();
        SocketIOClient secondClient = this.waitingRoomQueue.poll();

        return BattleRoom.builder()
                .uuid(createRoomName(firstClient))
                .firstClient(firstClient)
                .secondClient(secondClient)
                .build();
    }

    private String createRoomName(SocketIOClient client) {
        String newRoomUUID = UUID.randomUUID().toString();
        if (client.getNamespace().getRoomOperations(newRoomUUID).getClients().isEmpty())
            return newRoomUUID;
        else
            return createRoomName(client);
    }
}
