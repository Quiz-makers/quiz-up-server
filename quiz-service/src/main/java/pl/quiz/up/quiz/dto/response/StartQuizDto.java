package pl.quiz.up.quiz.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class StartQuizDto {
    private Long quizId;
    private String title;
    private String description;

    private Set<QuestionDto> questionDtoSet;

}
