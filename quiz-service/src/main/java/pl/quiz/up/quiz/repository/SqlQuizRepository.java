package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.QuizEntity;

@Repository
interface SqlQuizRepository extends QuizRepository, JpaRepository<QuizEntity, Long> {}
