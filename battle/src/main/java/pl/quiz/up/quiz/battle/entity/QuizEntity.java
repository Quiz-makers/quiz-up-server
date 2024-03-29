package pl.quiz.up.quiz.battle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quiz", schema = "public", catalog = "quiz_db")
public class QuizEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "quiz_id")
    private Long quizId;

    @Basic
    @Column(name = "quiz_code")
    private String quizCode;

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
    @Column(name = "type_id")
    private Long type;

    @Basic
    @Column(name = "category_id")
    private Long categoryId;

    @Basic
    @Column(name = "score")
    private Integer score;

    @Basic
    @Column(name = "slug")
    private String slug;

    @Basic
    @Column(name = "public_available")
    private Boolean publicAvailable;

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
    @Column(name = "public_from")
    private Timestamp publicFrom;

    @Basic
    @Column(name = "quiz_time")
    private Long quizTime;

    @Basic
    @Column(name = "starts_at")
    private Timestamp startsAt;

    @Basic
    @Column(name = "ends_at")
    private Timestamp endsAt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quizEntity", cascade = CascadeType.ALL)
    private List<QuizQuestionEntity> quizQuestionEntities;

}
