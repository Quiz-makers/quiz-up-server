package pl.quiz.up.quiz.repository.facade;

import pl.quiz.up.quiz.entity.QuizCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface QuizCategoryRepository {
    List<QuizCategoryEntity> findAll();

    Optional<String> findByCategoryId(long id);

}
