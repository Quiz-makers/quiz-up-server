package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "take", schema = "public", catalog = "quiz_db")
public class TakeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "take_id")
    private Long takeId;

    @Basic
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "quiz_id")
    private Long quizId;

    @Basic
    @Column(name = "take_status")
    private Short takeStatus;

    @Basic
    @Column(name = "score_result")
    private Short scoreResult;

    @Basic
    @Column(name = "comments")
    private String comments;

    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

    @Basic
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Basic
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

}
