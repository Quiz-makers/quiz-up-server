package pl.quiz.up.quiz.battle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionMessage {
    private long id;
    private String question;
    private List<AnswerDto> answers;
}
