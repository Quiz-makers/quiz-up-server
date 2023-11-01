package pl.quiz.up.quiz.battle.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.battle.dto.AnswerMessage;
import pl.quiz.up.quiz.battle.engine.BattleEngine;

@Component
@RequiredArgsConstructor
public class ClientRequestListener implements DataListener<AnswerMessage> {
    private final BattleEngine battleEngine;

    @Override
    public void onData(SocketIOClient socketIOClient, AnswerMessage answerMessage, AckRequest ackRequest) throws Exception {
        battleEngine.addClientAnswer(socketIOClient, answerMessage);
    }
}
