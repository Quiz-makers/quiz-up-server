package pl.quiz.up.quiz.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuizDto {

    private Long quizId;

    private String ownerName;

    private String ownerSurname;

    private String title;

    private String metaTitle;

    private String summary;

    private String description;

    private String type;

    private String category;

    private Short score;

    private String slug;

    private Boolean publicAvailable;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Timestamp publicFrom;

    private Long quizTime;

    private Timestamp startsAt;

    private Timestamp endsAt;

}
