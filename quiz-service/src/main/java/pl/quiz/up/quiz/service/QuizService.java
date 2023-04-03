package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.quiz.up.quiz.dto.QuizzesByCategoryReadDto;
import pl.quiz.up.quiz.dto.QuizzesReadDto;

@Service
@RequiredArgsConstructor
public final class QuizService {



    private QuizzesReadDto getQuizzes(final String category) {
        //TODO
    }

    private QuizzesByCategoryReadDto getQuizzesByCategory(final String category) {
        //TODO
    }
}
