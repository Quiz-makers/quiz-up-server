package pl.quiz.up.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.common.mapper.DTO;
import pl.quiz.up.common.utils.AuthenticationUtils;
import pl.quiz.up.quiz.dto.QuizWriteDto;
import pl.quiz.up.quiz.dto.response.CategoriesDto;
import pl.quiz.up.quiz.dto.response.QuizDto;
import pl.quiz.up.quiz.dto.response.QuizTypesDto;
import pl.quiz.up.quiz.entity.QuizEntity;
import pl.quiz.up.quiz.service.QuizService;

import java.util.Set;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @UserAuthority
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void publishQuiz(@DTO(QuizWriteDto.class) QuizEntity quiz) {

        quizService.publishQuiz(quiz);
    }

    @UserAuthority
    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable long id) {
        return ResponseEntity.ok(quizService.getQuizById(AuthenticationUtils.getUserId(), id));
    }

    @UserAuthority
    @GetMapping("/categories")
    public ResponseEntity<Set<CategoriesDto>> getCategories() {
        return ResponseEntity.ok(quizService.getCategories());
    }

    @UserAuthority
    @GetMapping("/types")
    public ResponseEntity<Set<QuizTypesDto>> getQuizTypes() {
        return ResponseEntity.ok(quizService.getQuizTypes());
    }

    @UserAuthority
    @GetMapping("/quizzes")
    public ResponseEntity<Set<QuizDto>> getAllPublicQuizzes() {

        return ResponseEntity.ok(
                quizService.getAllPublicQuizzes(AuthenticationUtils.getUserId()));
    }

    @UserAuthority
    @GetMapping("/quizzes/{category}")
    public ResponseEntity<Set<QuizDto>> getAllPublicQuizzesFromGivenCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                quizService.getAllPublicQuizzesFromGivenCategory(AuthenticationUtils.getUserId(), category));
    }

}
