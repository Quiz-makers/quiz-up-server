package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.repository.QuizRepository;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository repository;

    public StartQuizDto getQuizById(Long id) {
        return null;
    }
}
