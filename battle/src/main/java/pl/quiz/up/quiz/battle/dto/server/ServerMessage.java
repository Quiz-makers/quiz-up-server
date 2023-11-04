package pl.quiz.up.quiz.battle.dto.server;

import lombok.Getter;
import pl.quiz.up.quiz.battle.enums.ServerInfo;

import java.util.Date;


@Getter
public class ServerMessage {
    private ServerInfo serverInfo;
    private Object content;
    private Date date;

    private ServerMessage(ServerInfo serverInfo, Object content, Date date) {
        this.serverInfo = serverInfo;
        this.content = content;
        this.date = date;
    }

    private ServerMessage(ServerInfo serverInfo, Date date) {
        this.serverInfo = serverInfo;
        this.content = null;
        this.date = date;
    }

    public static ServerMessage of(ServerInfo serverInfo) {
        return switch (serverInfo) {
            case IN_WAITING_ROOM ->
                    new ServerMessage(ServerInfo.IN_WAITING_ROOM, "You have been added to the waiting room", new Date());
            case IN_BATTLE_ROOM ->
                    new ServerMessage(ServerInfo.IN_BATTLE_ROOM, "You have been added to the battle room", new Date());
            default -> new ServerMessage(ServerInfo.NONE, new Date());
        };
    }

    public static ServerMessage of(ServerInfo serverInfo, Object content) {
        return switch (serverInfo) {
            case IN_WAITING_ROOM -> new ServerMessage(ServerInfo.IN_WAITING_ROOM, content, new Date());
            case IN_BATTLE_ROOM -> new ServerMessage(ServerInfo.IN_BATTLE_ROOM, content, new Date());
            case QUESTION_MESSAGE -> new ServerMessage(ServerInfo.QUESTION_MESSAGE, content, new Date());
            case ANSWER_RESULT -> new ServerMessage(ServerInfo.ANSWER_RESULT, content, new Date());
            case BATTLE_RESULT -> new ServerMessage(ServerInfo.BATTLE_RESULT, content, new Date());
            case ERROR_MESSAGE -> new ServerMessage(ServerInfo.ERROR_MESSAGE, content, new Date());
            default -> new ServerMessage(ServerInfo.NONE, new Date());
        };
    }
}
