package pl.quiz.up.quiz.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.quiz.client.GPTServiceClient;
import pl.quiz.up.quiz.dto.QuizFullAnswerWriteDto;
import pl.quiz.up.quiz.dto.QuizFullQuestionWithAnswersWriteDto;
import pl.quiz.up.quiz.dto.QuizFullWriteDto;
import pl.quiz.up.quiz.dto.request.QuizFromTextGenerationDto;
import pl.quiz.up.quiz.dto.request.QuizFromTitleGenerationDto;
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
import pl.quiz.up.quiz.exception.QuizGenerationException;
import pl.quiz.up.quiz.repository.SqlQuizAnswerRepository;
import pl.quiz.up.quiz.repository.facade.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
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

    private final GPTServiceClient gptServiceClient;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

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
            saveQuiz(requestorId, quizDto);
        } else {
            throw new AlreadyExistsException(String.format("Quiz with name '%s' already exists", quizDto.getTitle()));
        }
    }

    @Transactional
    public QuizDto generateQuizFromTitle(String authToken, final Long requestorId, QuizFromTitleGenerationDto dto) {

        if(!quizRepository.existsByTitle(dto.getTitle())) {

            ResponseEntity<String> resp =
                    gptServiceClient.generateQuizFromTitle(authToken, dto);

            if(resp.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode =
                    parseJson(resp.getBody()).orElseThrow(() ->
                        new QuizGenerationException(String.format("Quiz '%s' generation fail. Please try again in a while", dto.getTitle())));

                return createQuizDto(
                        requestorId,
                        resp.getBody(),
                        jsonNode.deepCopy(),
                        dto.getNumberOfQuestions(),
                        dto.getAnswersPerQuestion(),
                        dto.getType(),
                        dto.getCategoryId(),
                        dto.getPublicAvailable(),
                        dto.getQuizTime(),
                        dto.getStartsAt(),
                        dto.getEndsAt());
            } else {
                throw new QuizGenerationException(String.format("Quiz '%s' generation fail. Please try again in a while", dto.getTitle()));
            }
        } else {
            throw new AlreadyExistsException(String.format("Quiz with name '%s' already exists", dto.getTitle()));
        }
    }

    @Transactional
    public QuizDto generateQuizFromText(String authToken, final Long requestorId, QuizFromTextGenerationDto dto) {

        ResponseEntity<String> resp =
                gptServiceClient.generateQuizFromText(authToken, dto);

        if(resp.getStatusCode() == HttpStatus.OK) {
            JsonNode jsonNode =
                    parseJson(resp.getBody()).orElseThrow(() ->
                            new QuizGenerationException(String.format("Quiz from text '%s' generation fail. Please try again in a while", dto.getText())));

            return createQuizDto(
                    requestorId,
                    resp.getBody(),
                    jsonNode.deepCopy(),
                    dto.getNumberOfQuestions(),
                    dto.getAnswersPerQuestion(),
                    dto.getType(),
                    dto.getCategoryId(),
                    dto.getPublicAvailable(),
                    dto.getQuizTime(),
                    dto.getStartsAt(),
                    dto.getEndsAt());
        } else {
            throw new QuizGenerationException(String.format("Quiz from text '%s' generation fail. Please try again in a while", dto.getText()));
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
            throw new AlreadyExistsException("The quiz is already present in favorites");
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

    private Optional<JsonNode> parseJson(String jsonString) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return Optional.of(jsonNode);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private QuizEntity saveQuiz(final Long requestorId, final QuizFullWriteDto quizDto) {
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
                                    .map(answer -> answer.toQuizAnswerEntity(savedQuestion))
                                    .collect(Collectors.toList()));
                });
        return savedQuiz;
    }

    private QuizDto createQuizDto(
            final Long requestorId,
            final String bodyResp,
            final JsonNode jsonNode,
            final int numberOfQuestions,
            final int answersPerQuestion,
            final Long type,
            final Long categoryId,
            final Boolean publicAvailable,
            final Long quizTime,
            final Timestamp startsAt,
            final Timestamp endsAt) {

        log.info("Quiz generation request success for requestor with ID = `{}`. Generated quiz content:\n{}",
                String.valueOf(requestorId),
                bodyResp);

        Map<String, QuizFullQuestionWithAnswersWriteDto> questions =
                IntStream.range(1, numberOfQuestions + 1)
                        .boxed()
                        .collect(Collectors.toMap(
                                i -> "q" + i,
                                i -> generateQuestion(
                                        jsonNode.path("quizQuestionsWithAnswersEntities").path("q" + i),
                                        answersPerQuestion),
                                (existing, replacement) -> replacement
                        ));

        QuizFullWriteDto quiz =
                QuizFullWriteDto.builder()
                        .title(jsonNode.get("title").asText())
                        .summary(jsonNode.get("summary").asText())
                        .description(jsonNode.get("description").asText())
                        .type(type)
                        .categoryId(categoryId)
                        .publicAvailable(publicAvailable)
                        .quizTime(quizTime)
                        .startsAt(startsAt)
                        .endsAt(endsAt)
                        .quizQuestionsWithAnswersEntities(questions)
                        .build();

        long id = saveQuiz(requestorId, quiz).getQuizId();

        return quizRepository.findQuiz(requestorId, id) // TODO -> change it to a more efficient solution
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + id));
    }

    private static QuizFullQuestionWithAnswersWriteDto generateQuestion(JsonNode questionNode, int answersPerQuestionNumber) {
        Set<QuizFullAnswerWriteDto> answers = IntStream.range(0, answersPerQuestionNumber)
                .mapToObj(i -> generateAnswer(questionNode.path("questionAnswersEntities").get(i)))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return QuizFullQuestionWithAnswersWriteDto.builder()
                .type(questionNode.path("type").asText())
                .question(questionNode.path("question").asText())
                .questionImage(questionNode.path("questionImage").asText())
                .score((short) questionNode.path("score").asInt())
                .difficultyLevel((short) questionNode.path("difficultyLevel").asInt())
                .visibleInQuiz(questionNode.path("visibleInQuiz").asBoolean())
                .questionAnswersEntities(answers)
                .build();
    }

    private static QuizFullAnswerWriteDto generateAnswer(JsonNode answerNode) {
        return QuizFullAnswerWriteDto.builder()
                .answer(answerNode.path("answer").asText())
                .correct(answerNode.path("correct").asBoolean())
                .active(answerNode.path("active").asBoolean())
                .build();
    }

}
