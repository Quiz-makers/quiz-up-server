package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;
import pl.quiz.up.quiz.repository.facade.QuizQuestionRepository;

@Repository
interface SqlQuizQuestionRepository extends QuizQuestionRepository, JpaRepository<QuizQuestionEntity, Long> {

}
