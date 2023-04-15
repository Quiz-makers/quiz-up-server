package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.quiz.dto.QuizFullWriteDto;
import pl.quiz.up.quiz.dto.response.CategoriesDto;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.dto.response.QuizTypesDto;
import pl.quiz.up.quiz.entity.FavoriteQuizzesEntity;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;
import pl.quiz.up.quiz.entity.SharedQuizzesEntity;
import pl.quiz.up.quiz.entity.composedKey.FavoriteQuizzesId;
import pl.quiz.up.quiz.entity.composedKey.SharedQuizzesId;
import pl.quiz.up.quiz.exception.NotFoundException;
import pl.quiz.up.quiz.exception.AlreadyExistsException;
import pl.quiz.up.quiz.repository.SqlQuizAnswerRepository;
import pl.quiz.up.quiz.repository.facade.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizCategoryRepository quizCategoriesRepository;

    private final QuizTypeRepository quizTypesRepository;

    private final QuizQuestionRepository quizQuestionRepository;

    private final SqlQuizAnswerRepository quizAnswerRepository;

    private final FavoriteQuizzesRepository favoriteQuizzesRepository;

    private final SharedQuizzesRepository sharedQuizzesRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void publishRawQuiz(final Long requestorId, final QuizEntity quiz) {

        if(!quizRepository.existsByTitle(quiz.getTitle())) {

            quiz.setOwnerId(requestorId);
            quiz.setQuizCode(generateQuizCode());
            quiz.setScore(0);
            quiz.setPublicAvailable(false);
            quiz.setSlug(generateQuizSlug(quiz.getTitle()));

            quizRepository.save(quiz);
        } else {
            throw new AlreadyExistsException(String.format("Quiz with name '%s' already exists", quiz.getTitle()));
        }
    }

    @Transactional
    public void publishQuizWithQuestionsAndAnswers(final Long requestorId, final QuizFullWriteDto quizDto) {

        if(!quizRepository.existsByTitle(quizDto.getTitle())) {

            QuizEntity savedQuiz =
                    quizRepository.save(
                            quizDto.toQuizEntity(requestorId, generateQuizCode(), generateQuizSlug(quizDto.getTitle())));

            quizDto.getQuizQuestionsWithAnswersEntities()
                    .values()
                    .forEach(questionWithAnswers -> {

                        QuizQuestionEntity savedQuestion =
                                quizQuestionRepository.save(questionWithAnswers.toQuizQuestionEntity(savedQuiz));

                        quizAnswerRepository.saveAll(
                                questionWithAnswers.getQuestionAnswersEntities().stream()
                                        .map(answer -> answer.toQuizAnswerEntity(savedQuiz, savedQuestion))
                                        .collect(Collectors.toList()));
                    });

        } else {
            throw new AlreadyExistsException(String.format("Quiz with name '%s' already exists", quizDto.getTitle()));
        }
    }

    @Transactional
    public void addQuizToFavorites(final Long requestorId, final long quidId) {

        FavoriteQuizzesId favoriteQuizzesId =
                FavoriteQuizzesId.builder()
                        .userId(requestorId)
                        .quizId(quidId)
                        .build();

        if(!favoriteQuizzesRepository.existsById(favoriteQuizzesId)) {
            if (quizRepository.checkIfOperationOnFavoritesMatchRequirements(requestorId, quidId)) {
                favoriteQuizzesRepository.save(
                        FavoriteQuizzesEntity.builder()
                                .id(favoriteQuizzesId)
                                .build());
            } else
                throw new NotFoundException(String.format("No quiz with quizId '%s' available", quidId));
        } else
            throw new AlreadyExistsException(String.format("The quiz is already present in favorites"));
    }

    @Transactional
    public QuizDto addQuizByQuizCode(final Long requestorId, final String quizCode) {

        QuizDto quizDto = quizRepository
                    .findQuizByQuizCode(requestorId, quizCode)
                    .orElseThrow(() -> new NotFoundException("No quiz found for quizCode: " + quizCode));

        SharedQuizzesId sharedQuizzesId =
                SharedQuizzesId.builder()
                .userId(requestorId)
                .quizId(quizDto.getQuizId())
                .build();

        if(!sharedQuizzesRepository.existsById(sharedQuizzesId)) {

            sharedQuizzesRepository.save(SharedQuizzesEntity.builder()
                    .id(sharedQuizzesId)
                    .build());
        } else
            throw new AlreadyExistsException(String.format("The quiz '%s' already exists in the collection", quizDto.getTitle()));

        return quizDto;
    }

    public QuizDto getQuizById(final Long requestorId, long quizId) {
        return quizRepository
                .findQuiz(requestorId, quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + quizId));
    }

    public Set<CategoriesDto> getCategories() {
        return quizCategoriesRepository
                .findAll()
                .stream()
                .map(item -> modelMapper.map(item, CategoriesDto.class))
                .collect(Collectors.toSet());
    }

    public Set<QuizTypesDto> getQuizTypes() {
        return quizTypesRepository
                .findAll()
                .stream()
                .map(item -> modelMapper.map(item, QuizTypesDto.class))
                .collect(Collectors.toSet());
    }

    public Set<QuizDto> getAllPublicQuizzes(final Long requestorId) {
        return quizRepository
                .findAllPubliclyAvailable(requestorId);
    }

    public Set<QuizDto> getAllUserQuizzes(final Long requestorId) {
        return quizRepository
                .findAllUserQuizzes(requestorId);
    }

    public Set<QuizDto> getAllUserFavoriteQuizzes(final Long requestorId) {
        return quizRepository
                .findAllUserFavoriteQuizzes(requestorId);
    }

    public Set<QuizDto> getAllUserMutualQuizzes(final Long requestorId) {
        return quizRepository
                .findAllUserMutualQuizzes(requestorId);
    }

    public Set<QuizDto> getAllPublicQuizzesFromGivenCategory(final Long requestorId, final String category) {
        return quizRepository
                .findAllPubliclyAvailableFromGivenCategory(requestorId, category);
    }

    @Transactional
    public void deleteQuizFromFavorites(final Long requestorId, final long quidId) {

        FavoriteQuizzesId favoriteQuizzesId =
                FavoriteQuizzesId.builder()
                        .userId(requestorId)
                        .quizId(quidId)
                        .build();

        if(favoriteQuizzesRepository.existsById(favoriteQuizzesId)) {
                favoriteQuizzesRepository.deleteById(favoriteQuizzesId);
        } else
            throw new NotFoundException(String.format("No quiz with quizId '%s' in favorites", quidId));
    }

    private String generateQuizSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

    // TODO -> Add unique quiz code generator
    private static String generateQuizCode() {
        return UUID
                .randomUUID()
                .toString()
                .substring(0, 15)
                .replaceAll("-", "x");
    }

}
