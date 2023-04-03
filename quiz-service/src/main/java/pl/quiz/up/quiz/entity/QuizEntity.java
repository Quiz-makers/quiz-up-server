package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "quiz", schema = "public", catalog = "quiz_db")
public class QuizEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "quiz_id")
    private Long quizId;
    @Basic
    @Column(name = "owner_id")
    private Long ownerId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "meta_title")
    private String metaTitle;
    @Basic
    @Column(name = "summary")
    private String summary;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "type")
    private Short type;
    @Basic
    @Column(name = "category_id")
    private Long categoryId;
    @Basic
    @Column(name = "score")
    private Short score;
    @Basic
    @Column(name = "slug")
    private String slug;
    @Basic
    @Column(name = "public_available")
    private Boolean publicAvailable;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "public_from")
    private Timestamp publicFrom;
    @Basic
    @Column(name = "quiz_time")
    private Object quizTime;
    @Basic
    @Column(name = "starts_at")
    private Timestamp startsAt;
    @Basic
    @Column(name = "ends_at")
    private Timestamp endsAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quizEntity")
    private Set<QuizQuestionEntity> quizQuestionEntities;
}
