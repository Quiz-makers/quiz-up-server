package pl.quiz.up.quiz.repository;

import pl.quiz.up.quiz.entity.QuizEntity;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {

    QuizEntity save(QuizEntity entity);

    List<QuizEntity> findAll();

    List<QuizEntity> findAllByCategoryId(final long categoryId);

    Optional<QuizEntity> findByQuizId(Long id);

}
