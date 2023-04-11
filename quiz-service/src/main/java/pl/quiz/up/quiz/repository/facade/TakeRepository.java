package pl.quiz.up.quiz.repository.facade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.entity.TakeEntity;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TakeRepository extends JpaRepository<TakeEntity, Long> {


}
