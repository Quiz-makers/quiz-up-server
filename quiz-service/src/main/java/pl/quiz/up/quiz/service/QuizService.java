package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.exception.QuizNotFoundException;
import pl.quiz.up.quiz.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public final class QuizService {

    private final QuizRepository repository;
    private final ModelMapper modelMapper;

//    void publishQuiz(final QuizEntity quiz) {
//        //TODO
//    }

    public StartQuizDto getQuizById(Long id) {
        return repository
                .findById(id)
                .map(item -> modelMapper.map(item, StartQuizDto.class))
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
