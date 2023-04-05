package pl.quiz.up.quiz.dto.response;

import lombok.*;
import pl.quiz.up.quiz.entity.QuizEntity;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StartQuizDto {
    private Long quizId;
    private String title;
    private String description;

    private Set<QuestionDto> questionDtoSet;

}
