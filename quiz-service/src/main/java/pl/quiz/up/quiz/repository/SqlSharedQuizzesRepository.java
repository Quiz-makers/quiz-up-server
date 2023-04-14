package pl.quiz.up.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.quiz.up.quiz.entity.SharedQuizzesEntity;
import pl.quiz.up.quiz.entity.composedKey.SharedQuizzesId;
import pl.quiz.up.quiz.repository.facade.SharedQuizzesRepository;

@Repository
interface SqlSharedQuizzesRepository
        extends SharedQuizzesRepository, JpaRepository<SharedQuizzesEntity, SharedQuizzesId> {

}
