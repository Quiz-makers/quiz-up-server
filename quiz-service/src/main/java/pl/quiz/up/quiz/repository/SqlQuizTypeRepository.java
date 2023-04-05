package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.quiz.up.quiz.entity.QuizTypeEntity;
import pl.quiz.up.quiz.repository.facade.QuizTypeRepository;

interface SqlQuizTypeRepository extends QuizTypeRepository, JpaRepository<QuizTypeEntity, Long> {
}
