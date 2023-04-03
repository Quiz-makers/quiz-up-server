package pl.quiz.up.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.mapper.DTO;
import pl.quiz.up.common.messages.MessagesEnum;
import pl.quiz.up.common.messages.Translator;
import pl.quiz.up.quiz.dto.QuizzesByCategoryReadDto;
import pl.quiz.up.quiz.dto.QuizzesReadDto;
import pl.quiz.up.quiz.service.QuizService;


@RestController
@RequiredArgsConstructor
@Log4j2
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@DTO(QuizWriteDto.class) Quiz quiz) {

        quizService.save(quiz);
    }

    @GetMapping("/test")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> startQuizResolving() {
        return ResponseEntity.ok(Translator.translate(MessagesEnum.BAD_EMAIL));
    }

    @GetMapping("/quizzes")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<QuizzesReadDto> getQuizzes() {

        return ResponseEntity.ok(quizService.getQuizzes(category));
    }

    @GetMapping("/quizzes/{category}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<QuizzesByCategoryReadDto> getQuizzesByCategory(@PathVariable String category) {

        return ResponseEntity.ok(quizService.getQuizzesByCategory(category));
    }

}
