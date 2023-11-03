package pl.quiz.up.quiz.battle.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.battle.engine.BattleEngine;

@Component
@RequiredArgsConstructor
public class DisconnectListenerImpl implements DisconnectListener {
    private final BattleEngine battleEngine;

    @Override
    public void onDisconnect(SocketIOClient socketIOClient) {
        this.battleEngine.leaveWaitingRoom(socketIOClient);
    }
}
