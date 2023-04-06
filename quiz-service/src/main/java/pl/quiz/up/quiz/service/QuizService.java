package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.common.utils.AuthenticationUtils;
import pl.quiz.up.quiz.dto.response.CategoriesDto;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.dto.response.QuizTypesDto;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.exception.IllegalQuizOwnerIdException;
import pl.quiz.up.quiz.exception.QuizNotFoundException;
import pl.quiz.up.quiz.repository.facade.QuizCategoryRepository;
import pl.quiz.up.quiz.repository.facade.QuizRepository;
import pl.quiz.up.quiz.repository.facade.QuizTypeRepository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizCategoryRepository quizCategoriesRepository;

    private final QuizTypeRepository quizTypesRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public void publishQuiz(final QuizEntity quiz) {

        Long requestorId = AuthenticationUtils.getUserId();

        if(Objects.equals(quiz.getOwnerId(), requestorId)) {

            quiz.setSlug(generateQuizSlug(quiz.getTitle()));

            quizRepository.save(quiz);
        } else {
            throw new IllegalQuizOwnerIdException("Invalid quiz owner id value");
        }
    }

    public QuizDto getQuizById(final Long requestorId, long quizId) {
        return quizRepository
                .findQuiz(requestorId, quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + quizId));
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

    public Set<QuizDto> getAllPublicQuizzesFromGivenCategory(final Long requestorId, final String category) {
        return quizRepository
                .findAllPubliclyAvailableFromGivenCategory(requestorId, category);
    }

    private String generateQuizSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
}
