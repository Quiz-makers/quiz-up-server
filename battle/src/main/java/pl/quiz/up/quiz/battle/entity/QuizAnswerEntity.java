package pl.quiz.up.quiz.battle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quiz_answer", schema = "public", catalog = "quiz_db")
public class QuizAnswerEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "answer_id")
    private Long answerId;

//    @Basic
//    @Column(name = "quiz_id")
//    private Long quizId;

    @Basic
    @Column(name = "answer")
    private String answer;

    @Basic
    @Column(name = "correct")
    private Boolean correct;

    @Basic
    @Column(name = "active")
    private Boolean active;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id", nullable=false)
    private QuizQuestionEntity quizQuestionEntity;
}
