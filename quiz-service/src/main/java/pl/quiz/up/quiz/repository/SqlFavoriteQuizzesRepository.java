package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.FavoriteQuizzesEntity;
import pl.quiz.up.quiz.entity.composedKey.FavoriteQuizzesId;
import pl.quiz.up.quiz.repository.facade.FavoriteQuizzesRepository;

@Repository
interface SqlFavoriteQuizzesRepository
        extends FavoriteQuizzesRepository, JpaRepository<FavoriteQuizzesEntity, FavoriteQuizzesId> {

}
