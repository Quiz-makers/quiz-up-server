package pl.quiz.up.quiz.battle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.battle.entity.TakeAnswerEntity;

@Repository
public interface TakeAnswerRepository extends JpaRepository<TakeAnswerEntity, Long> {

}
