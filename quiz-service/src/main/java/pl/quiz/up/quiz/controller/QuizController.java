package pl.quiz.up.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.service.QuizService;


@RestController
@RequiredArgsConstructor
@Log4j2
public final class QuizController {

    private final QuizService quizService;

//    @PostMapping("/quiz")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void publishQuiz(@DTO(QuizWriteDto.class) QuizEntity quiz) {
//
//        quizService.publishQuiz(quiz);
//    }

    @GetMapping("/start/{id}")
    @UserAuthority
    public ResponseEntity<StartQuizDto> startQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

//    @GetMapping("/quizzes")
////    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<AllQuizzesReadDto> getAllQuizzes() {
//
//        return ResponseEntity.ok(quizService.getAllQuizzes());
//    }
//
//    @GetMapping("/quizzes/{category}")
////    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<AllQuizzesFromCategoryReadDto> getAllQuizzesFromCategory(@PathVariable String category) {
//
//        return ResponseEntity.ok(quizService.getAllQuizzesFromCategory(category));
//    }

}
