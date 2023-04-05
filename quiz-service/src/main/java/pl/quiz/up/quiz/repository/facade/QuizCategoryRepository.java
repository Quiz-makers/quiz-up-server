package pl.quiz.up.quiz.repository.facade;

import pl.quiz.up.quiz.entity.QuizCategoryEntity;

import java.util.List;

public interface QuizCategoryRepository {
    List<QuizCategoryEntity> findAll();
}
