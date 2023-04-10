package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "quiz_meta", schema = "public", catalog = "quiz_db")
public class QuizMetaEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "quiz_id")
    private Long quizId;

    @Basic
    @Column(name = "key")
    private String key;

    @Basic
    @Column(name = "metadata")
    private String metadata;

    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

}
