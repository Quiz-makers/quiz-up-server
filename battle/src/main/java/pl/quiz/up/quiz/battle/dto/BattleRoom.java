package pl.quiz.up.quiz.battle.dto;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BattleRoom {
    private String uuid;
    private SocketIOClient firstClient;
    private SocketIOClient secondClient;
}
