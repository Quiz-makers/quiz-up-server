package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "take_answer", schema = "public", catalog = "quiz_db")
public class TakeAnswerEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "take_id", referencedColumnName = "take_id")
    private TakeEntity takeEntity;

    @Basic
    @Column(name = "question_id")
    private Long questionId;

    @Basic
    @Column(name = "answer_id")
    private Long answerId;

    @Basic
    @Column(name = "open_text_answer")
    private String openTextAnswer;

    @Basic
    @Column(name = "most_actual_answer")
    private Boolean mostActualAnswer;

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

}
