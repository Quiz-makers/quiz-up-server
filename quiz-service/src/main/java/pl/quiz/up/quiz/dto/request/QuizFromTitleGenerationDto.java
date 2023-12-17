package pl.quiz.up.quiz.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.quiz.up.common.messages.Messages;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class QuizFromTitleGenerationDto implements Serializable {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String title;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Long type;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Long categoryId;

    @NotNull(message = Messages.EMPTY_FIELD)
    private int numberOfQuestions;

    @NotNull(message = Messages.EMPTY_FIELD)
    private int answersPerQuestion;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Boolean publicAvailable;

    private Long quizTime;

    private Timestamp startsAt;

    private Timestamp endsAt;

}
