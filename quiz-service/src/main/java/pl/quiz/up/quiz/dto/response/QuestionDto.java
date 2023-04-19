package pl.quiz.up.quiz.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionDto {
    private Long questionId;
    private String question;
    private String image;
    private List<AnswerDto> answerDtoSet;

}
