package pl.quiz.up.quiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.common.messages.Messages;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizFullQuestionWithAnswersWriteDto {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String type;

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String question;

    private byte[] questionImage;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Short score;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Short difficultyLevel;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Boolean visibleInQuiz;

    @Valid
    private Set<QuizFullAnswerWriteDto> questionAnswersEntities;

    public Set<QuizFullAnswerWriteDto> getQuestionAnswersEntities() {
        return questionAnswersEntities;
    }

    public QuizQuestionEntity toQuizQuestionEntity(QuizEntity parentQuiz) {
        return QuizQuestionEntity.builder()
                .quizEntity(parentQuiz)
                .type(this.type)
                .question(this.question)
                .questionImage(this.questionImage)
                .score(this.score)
                .difficultyLevel(this.difficultyLevel)
                .visibleInQuiz(this.visibleInQuiz)
                .build();
    }

}
