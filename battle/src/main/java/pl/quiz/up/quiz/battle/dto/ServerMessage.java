package pl.quiz.up.quiz.battle.dto;

import lombok.Getter;
import pl.quiz.up.quiz.battle.enums.ServerInfo;

import java.util.Date;


@Getter
public class ServerMessage {
    private ServerInfo serverInfo;
    private String message;
    private Date date;

    private ServerMessage(ServerInfo serverInfo, String message, Date date) {
        this.serverInfo = serverInfo;
        this.message = message;
        this.date = date;
    }

    public static ServerMessage of(ServerInfo serverInfo) {
        return switch (serverInfo) {
            case IN_WAITING_ROOM ->
                    new ServerMessage(ServerInfo.IN_WAITING_ROOM, "You have been added to the waiting room", new Date());
            case IN_BATTLE_ROOM ->
                    new ServerMessage(ServerInfo.IN_BATTLE_ROOM, "You have been added to the battle room", new Date());

        };
    }
}
