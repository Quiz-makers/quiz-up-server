package pl.quiz.up.quiz.dto.response;

import lombok.Builder;
import lombok.Data;
import pl.quiz.up.quiz.entity.QuizEntity;

import java.util.Set;

@Data
@Builder
public class QuizDto {
    private Long quizId;
    private String title;
    private String description;

    private Set<QuestionDto> questionDtoSet;

    public static QuizDto toDto(QuizEntity quiz) {
        return QuizDto
                .builder()
                .quizId(quiz.getQuizId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .build();
    }
}
