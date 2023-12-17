package pl.quiz.up.quiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.common.messages.Messages;
import pl.quiz.up.quiz.entity.QuizEntity;

import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizFullWriteDto {

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

    @NotNull(message = Messages.EMPTY_FIELD)
    private Boolean publicAvailable;

    private Long quizTime;

    private Timestamp startsAt;

    private Timestamp endsAt;

    @Valid
    private Map<String, QuizFullQuestionWithAnswersWriteDto> quizQuestionsWithAnswersEntities;

    public QuizEntity toQuizEntity(long ownerId, String quizCode, String slug) {
        return QuizEntity.builder()
                .ownerId(ownerId)
                .quizCode(quizCode)
                .title(this.title)
                .metaTitle(this.metaTitle)
                .summary(this.summary)
                .description(this.description)
                .type(this.type)
                .categoryId(this.categoryId)
                .score(quizQuestionsWithAnswersEntities.values().stream().mapToInt(QuizFullQuestionWithAnswersWriteDto::getScore).sum())
                .slug(slug)
                .publicAvailable(this.publicAvailable)
                .publicFrom(this.publicAvailable ? new Timestamp(System.currentTimeMillis()) : null)
                .quizTime(this.quizTime)
                .startsAt(this.startsAt)
                .endsAt(this.endsAt)
                .build();
    }
}
