package pl.quiz.up.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.common.messages.Messages;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRawWriteDto {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String title;

    private String metaTitle;

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String summary;

    private String description;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Long type;

    @NotNull(message = Messages.EMPTY_FIELD)
    private Long categoryId;

    private Long quizTime;

    private Timestamp startsAt;

    private Timestamp endsAt;

}
