package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.QuizCategoryEntity;
import pl.quiz.up.quiz.repository.facade.QuizCategoryRepository;

@Repository
interface SqlQuizCategoryRepository extends QuizCategoryRepository, JpaRepository<QuizCategoryEntity, Long> {

}
