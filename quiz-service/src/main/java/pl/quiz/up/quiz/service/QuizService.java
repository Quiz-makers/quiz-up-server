package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.dto.AllQuizzesFromCategoryReadDto;
import pl.quiz.up.quiz.dto.AllQuizzesReadDto;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.exception.QuizNotFoundException;
import pl.quiz.up.quiz.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public final class QuizService {

    private final QuizRepository repository;

//    void publishQuiz(final QuizEntity quiz) {
//        //TODO
//    }

    public QuizDto getQuizById(Long id) {
        return repository
                .findByQuizId(id)
                .map(QuizDto::toDto)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
    }

//    public AllQuizzesReadDto getAllQuizzes() {
//        //TODO
//    }
//
//    public AllQuizzesFromCategoryReadDto getAllQuizzesFromCategory(final String category) {
//        //TODO
//    }

}
