package pl.quiz.up.quiz.battle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.quiz.battle.enums.ServerInfo;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ServerMessage {
    private ServerInfo serverInfo;
    private String message;
    private Date date;
}
