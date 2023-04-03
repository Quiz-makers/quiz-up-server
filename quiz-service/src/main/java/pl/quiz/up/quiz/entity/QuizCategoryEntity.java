package pl.quiz.up.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Data
@Entity
@Table(name = "quiz_category", schema = "public", catalog = "quiz_db")
public class QuizCategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id")
    private Long categoryId;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "thumbnail")
    private byte[] thumbnail;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

}
