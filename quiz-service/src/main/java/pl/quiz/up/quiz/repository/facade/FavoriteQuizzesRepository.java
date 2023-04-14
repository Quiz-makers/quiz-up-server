package pl.quiz.up.quiz.repository.facade;

import pl.quiz.up.quiz.entity.FavoriteQuizzesEntity;
import pl.quiz.up.quiz.entity.composedKey.FavoriteQuizzesId;

public interface FavoriteQuizzesRepository {

    FavoriteQuizzesEntity save(FavoriteQuizzesEntity entity);

    boolean existsById(FavoriteQuizzesId id);

    void deleteById(FavoriteQuizzesId id);

}
