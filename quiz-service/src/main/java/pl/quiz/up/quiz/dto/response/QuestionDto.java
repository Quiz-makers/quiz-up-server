package pl.quiz.up.quiz.dto.response;

import lombok.Data;

import java.util.Set;

@Data
public class QuestionDto {
    private Long questionId;
    private String question;

    private Set<AnswerDto> answerDtoSet;

}
