package pl.quiz.up.quiz.battle.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.battle.dto.AnswerMessage;
import pl.quiz.up.quiz.battle.listener.ClientRequestListener;
import pl.quiz.up.quiz.battle.listener.ConnectListenerImpl;
import pl.quiz.up.quiz.battle.listener.DisconnectListenerImpl;

import static pl.quiz.up.quiz.battle.utils.Constants.EVENT_BATTLE_ANSWER;

@Component
@Slf4j
public class SocketModule {

    public SocketModule(SocketIOServer server, ConnectListenerImpl connectListenerImpl, ClientRequestListener clientRequestListener, DisconnectListenerImpl disconnectListener) {
        server.addConnectListener(connectListenerImpl);
        server.addDisconnectListener(disconnectListener);
        server.addEventListener(EVENT_BATTLE_ANSWER, AnswerMessage.class, clientRequestListener);
        server.start();
    }
}
