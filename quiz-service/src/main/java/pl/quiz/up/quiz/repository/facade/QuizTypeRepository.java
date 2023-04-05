package pl.quiz.up.quiz.repository.facade;

import pl.quiz.up.quiz.entity.QuizTypeEntity;

import java.util.List;

public interface QuizTypeRepository {
    List<QuizTypeEntity> findAll();
}
