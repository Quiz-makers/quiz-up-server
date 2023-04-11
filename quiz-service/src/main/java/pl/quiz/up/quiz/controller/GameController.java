package pl.quiz.up.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.quiz.dto.request.FinishQuizDto;
import pl.quiz.up.quiz.dto.response.QuizResultDto;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.service.GameService;

@RestController
@RequiredArgsConstructor
@Log4j2
class GameController {

    private final GameService gameService;

    @GetMapping("/start/{id}")
    @UserAuthority
    public ResponseEntity<StartQuizDto> startQuiz(@PathVariable long id) {
        return ResponseEntity.ok(gameService.startQuiz(id));
    }

    @PostMapping("/finish")
    @UserAuthority
    public ResponseEntity<QuizResultDto> finishQuiz(@RequestBody FinishQuizDto finishQuizDto) {
        return ResponseEntity.ok(gameService.finishQuiz(finishQuizDto));
    }

}
