package pl.quiz.up.quiz.battle.dto.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {
    private long id;
    private String answer;
    private Boolean isCorrect;
}
