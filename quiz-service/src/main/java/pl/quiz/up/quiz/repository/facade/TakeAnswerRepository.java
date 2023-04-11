package pl.quiz.up.quiz.repository.facade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.TakeAnswerEntity;
import pl.quiz.up.quiz.entity.TakeEntity;

@Repository
public interface TakeAnswerRepository extends JpaRepository<TakeAnswerEntity, Long> {


}
