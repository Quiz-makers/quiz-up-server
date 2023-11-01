package pl.quiz.up.quiz.battle.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.battle.dto.AnswerMessage;
import pl.quiz.up.quiz.battle.listener.ClientRequestListener;
import pl.quiz.up.quiz.battle.listener.ConnectListenerImpl;

@Component
@Slf4j
public class SocketModule {

    public SocketModule(SocketIOServer server, ConnectListenerImpl connectListenerImpl, ClientRequestListener clientRequestListener) {
        server.addConnectListener(connectListenerImpl);
        server.addEventListener("battle_answer", AnswerMessage.class, clientRequestListener);
        server.start();
    }


//    private DisconnectListener onDisconnected() {
//        return client -> {
//            var params = client.getHandshakeData().getUrlParams();
//            String room = params.get("room").stream().collect(Collectors.joining());
//            String username = params.get("username").stream().collect(Collectors.joining());
//            socketService.saveInfoMessage(client, String.format(Constants.DISCONNECT_MESSAGE, username), room);
//            log.info("Socket ID[{}] - room[{}] - username [{}]  discnnected to chat module through", client.getSessionId().toString(), room, username);
//        };
//    }


}
