package pl.quiz.up.quiz.repository.facade;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.entity.QuizEntity;

import java.util.Optional;
import java.util.Set;

public interface QuizRepository {

    QuizEntity save(QuizEntity entity);

    Optional<QuizEntity> findByQuizId(long id);

    @Query("""
        SELECT
            q.quizId AS quizId,
            q.title AS title,
            q.metaTitle AS metaTitle,
            owner.name AS ownerName,
            owner.surname AS ownerSurname,
            q.summary AS summary,
            q.description AS description,
            t.type AS type,
            cat.category AS category,
            q.score AS score,
            q.slug AS slug,
            q.publicAvailable AS publicAvailable,
            q.createdAt AS createdAt,
            q.updatedAt AS updatedAt,
            q.publicFrom AS publicFrom,
            q.quizTime AS quizTime,
            q.startsAt AS startsAt,
            q.endsAt AS endsAt
        FROM QuizEntity q
        INNER JOIN system_user owner ON q.ownerId = owner.id
        INNER JOIN QuizTypeEntity t ON q.type = t.typeId
        INNER JOIN QuizCategoryEntity cat ON q.categoryId = cat.categoryId
        WHERE
            q.quizId = :quizId AND
            q.ownerId = :requestorId OR q.publicAvailable = TRUE
          
    """)
    Optional<QuizDto> findQuiz(@Param("requestorId") long requestorId, @Param("quizId") long quizId);

    @Query("""
        SELECT
            q.quizId AS quizId,
            q.title AS title,
            q.metaTitle AS metaTitle,
            owner.name AS ownerName,
            owner.surname AS ownerSurname,
            q.summary AS summary,
            q.description AS description,
            t.type AS type,
            cat.category AS category,
            q.score AS score,
            q.slug AS slug,
            q.publicAvailable AS publicAvailable,
            q.createdAt AS createdAt,
            q.updatedAt AS updatedAt,
            q.publicFrom AS publicFrom,
            q.quizTime AS quizTime,
            q.startsAt AS startsAt,
            q.endsAt AS endsAt
        FROM QuizEntity q
        INNER JOIN system_user owner ON q.ownerId = owner.id
        INNER JOIN QuizTypeEntity t ON q.type = t.typeId
        INNER JOIN QuizCategoryEntity cat ON q.categoryId = cat.categoryId
        WHERE
            q.publicAvailable = TRUE AND
            q.ownerId <> :requestorId
    """)
    Set<QuizDto> findAllPubliclyAvailable(@Param("requestorId") long requestorId);

    @Query("""
        SELECT
            q.quizId AS quizId,
            q.title AS title,
            q.metaTitle AS metaTitle,
            owner.name AS ownerName,
            owner.surname AS ownerSurname,
            q.summary AS summary,
            q.description AS description,
            t.type AS type,
            cat.category AS category,
            q.score AS score,
            q.slug AS slug,
            q.publicAvailable AS publicAvailable,
            q.createdAt AS createdAt,
            q.updatedAt AS updatedAt,
            q.publicFrom AS publicFrom,
            q.quizTime AS quizTime,
            q.startsAt AS startsAt,
            q.endsAt AS endsAt
        FROM QuizEntity q
        INNER JOIN system_user owner ON q.ownerId = owner.id
        INNER JOIN QuizTypeEntity t ON q.type = t.typeId
        INNER JOIN QuizCategoryEntity cat ON q.categoryId = cat.categoryId
        WHERE
            q.publicAvailable = TRUE AND
            cat.category = :category AND
            q.ownerId <> :requestorId
    """)
    Set<QuizDto> findAllPubliclyAvailableFromGivenCategory(@Param("requestorId") long requestorId, @Param("category") final String category);

    @Query("""
        SELECT
            q.quizId AS quizId,
            q.title AS title,
            q.metaTitle AS metaTitle,
            owner.name AS ownerName,
            owner.surname AS ownerSurname,
            q.summary AS summary,
            q.description AS description,
            t.type AS type,
            cat.category AS category,
            q.score AS score,
            q.slug AS slug,
            q.publicAvailable AS publicAvailable,
            q.createdAt AS createdAt,
            q.updatedAt AS updatedAt,
            q.publicFrom AS publicFrom,
            q.quizTime AS quizTime,
            q.startsAt AS startsAt,
            q.endsAt AS endsAt
        FROM QuizEntity q
        INNER JOIN system_user owner ON q.ownerId = owner.id
        INNER JOIN QuizTypeEntity t ON q.type = t.typeId
        INNER JOIN QuizCategoryEntity cat ON q.categoryId = cat.categoryId
        WHERE
            q.ownerId = :requestorId
    """)
    Set<QuizDto> findAllUserQuizzes(@Param("requestorId") long requestorId);

}
