package pl.quiz.up.gpt.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.quiz.up.common.messages.Messages;

import java.io.Serializable;

@Data
public class QuizFromTitleDto implements Serializable {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String title;

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String category;

    @NotNull(message = Messages.EMPTY_FIELD)
    private int numberOfQuestions;

    @NotNull(message = Messages.EMPTY_FIELD)
    private int answersPerQuestion;

}
