package pl.quiz.up.quiz.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.battle.entity.QuizQuestionEntity;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestionEntity, Long> {

    @Query("select q from QuizQuestionEntity q order by RANDOM() LIMIT 5")
    List<QuizQuestionEntity> findRandomQuiz();
}
