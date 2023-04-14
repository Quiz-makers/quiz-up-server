package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.quiz.up.quiz.entity.composedKey.FavoriteQuizzesId;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_favorite_quizzes", schema = "public", catalog = "quiz_db")
public class FavoriteQuizzesEntity {

    @EmbeddedId
    private FavoriteQuizzesId id;

    @Basic
    @Column(name = "added_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp addedAt;

}
