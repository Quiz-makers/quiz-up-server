package pl.quiz.up.quiz.repository.facade;

import pl.quiz.up.quiz.entity.SharedQuizzesEntity;
import pl.quiz.up.quiz.entity.composedKey.SharedQuizzesId;

public interface SharedQuizzesRepository {

    SharedQuizzesEntity save(SharedQuizzesEntity entity);

    boolean existsById(SharedQuizzesId id);

}
