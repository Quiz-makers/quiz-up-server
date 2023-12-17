package pl.quiz.up.quiz.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.common.mapper.DTO;
import pl.quiz.up.common.utils.AuthenticationUtils;
import pl.quiz.up.quiz.dto.QuizFullWriteDto;
import pl.quiz.up.quiz.dto.QuizRawWriteDto;
import pl.quiz.up.quiz.dto.request.QuizFromTextGenerationDto;
import pl.quiz.up.quiz.dto.request.QuizFromTitleGenerationDto;
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
    public void publishQuizWithQuestionsAndAnswers(@RequestBody @Valid QuizFullWriteDto quizDto) {

        quizService
                .publishQuizWithQuestionsAndAnswers(AuthenticationUtils.getUserId(), quizDto);
    }

    @UserAuthority
    @PostMapping("/raw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void publishRawQuiz(@DTO(QuizRawWriteDto.class) QuizEntity quiz) {

        quizService.publishRawQuiz(AuthenticationUtils.getUserId(), quiz);
    }

    @UserAuthority
    @PostMapping(value = "/generate/fromTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> generateQuizFromTitle(HttpServletRequest req, @RequestBody @Valid QuizFromTitleGenerationDto dto) {
        return ResponseEntity.ok(
                quizService.generateQuizFromTitle(
                        req.getHeader(HttpHeaders.AUTHORIZATION),
                        AuthenticationUtils.getUserId(),
                        dto));
    }

    @UserAuthority
    @PostMapping(value = "/generate/fromText", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuizDto> generateQuizFromText(HttpServletRequest req, @RequestBody @Valid QuizFromTextGenerationDto dto) {
        return ResponseEntity.ok(
                quizService.generateQuizFromText(
                        req.getHeader(HttpHeaders.AUTHORIZATION),
                        AuthenticationUtils.getUserId(),
                        dto));
    }

    @UserAuthority
    @PostMapping("/favorite/{quidId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addQuizToFavorites(@PathVariable long quidId) {

        quizService.addQuizToFavorites(AuthenticationUtils.getUserId(), quidId);
    }

    @UserAuthority
    @GetMapping
    public ResponseEntity<QuizDto> getQuizByQuizCode(@RequestHeader("quizCode") final String quizCode) {
        return ResponseEntity.ok(quizService.addQuizByQuizCode(AuthenticationUtils.getUserId(), quizCode));
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

    @UserAuthority
    @GetMapping("/quizzes/user")
    public ResponseEntity<Set<QuizDto>> getAllUserQuizzes() {

        return ResponseEntity.ok(
                quizService.getAllUserQuizzes(AuthenticationUtils.getUserId()));
    }

    @UserAuthority
    @GetMapping("/quizzes/user/favorite")
    public ResponseEntity<Set<QuizDto>> getAllUserFavoriteQuizzes() {

        return ResponseEntity.ok(
                quizService.getAllUserFavoriteQuizzes(AuthenticationUtils.getUserId()));
    }

    @UserAuthority
    @GetMapping("/quizzes/user/mutual")
    public ResponseEntity<Set<QuizDto>> getAllUserMutualQuizzes() {

        return ResponseEntity.ok(
                quizService.getAllUserMutualQuizzes(AuthenticationUtils.getUserId()));
    }

    @UserAuthority
    @DeleteMapping("/favorite/{quidId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuizFromFavorites(@PathVariable long quidId) {

        quizService.deleteQuizFromFavorites(AuthenticationUtils.getUserId(), quidId);
    }

}
