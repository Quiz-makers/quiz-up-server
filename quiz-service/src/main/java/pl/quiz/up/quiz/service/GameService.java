package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.exception.QuizNotFoundException;
import pl.quiz.up.quiz.repository.facade.QuizRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository repository;

    private final ModelMapper modelMapper;

    public StartQuizDto startQuiz(long id) {
        return repository
                .findByQuizId(id)
                .map(item -> modelMapper.map(item, StartQuizDto.class))
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id: " + id));
    }
}
