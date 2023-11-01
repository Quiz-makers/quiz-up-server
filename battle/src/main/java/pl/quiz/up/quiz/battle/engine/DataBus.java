package pl.quiz.up.quiz.battle.engine;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.NoArgsConstructor;
import pl.quiz.up.quiz.battle.dto.AnswerMessage;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class DataBus {
    private final ConcurrentHashMap<SocketIOClient, AnswerMessage> dataStorage = new ConcurrentHashMap<>();

    public void addResponse(SocketIOClient client, AnswerMessage answerMessage) {
        dataStorage.put(client, answerMessage);
    }

    public AnswerMessage getMessage(SocketIOClient client) {
        if (dataStorage.containsKey(client)) {
            AnswerMessage message =  dataStorage.get(client);
            dataStorage.remove(client, message);
            return message;
        }

        return null;
    }
}
