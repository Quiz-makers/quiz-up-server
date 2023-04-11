package pl.quiz.up.quiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishQuizAnswerDto {
    private Long questionId;
    private Long answerId;
}
