package pl.quiz.up.quiz.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AnswerDto {
    private Long id;
    private String answer;
    private Boolean isCorrect;
}
