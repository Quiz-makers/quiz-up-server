package pl.quiz.up.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.common.messages.Messages;
import pl.quiz.up.quiz.entity.QuizAnswerEntity;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizFullAnswerWriteDto {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String answer;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Boolean correct;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Boolean active;

    public QuizAnswerEntity toQuizAnswerEntity(QuizEntity parentQuiz, QuizQuestionEntity parentQuestion) {
        return QuizAnswerEntity.builder()
                .quizEntity(parentQuiz)
                .quizQuestionEntity(parentQuestion)
                .answer(this.answer)
                .correct(this.correct)
                .active(this.active)
                .build();
    }

}
