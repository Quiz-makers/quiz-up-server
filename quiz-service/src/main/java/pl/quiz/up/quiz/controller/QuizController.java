package pl.quiz.up.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.common.messages.MessagesEnum;
import pl.quiz.up.common.messages.Translator;


@RestController
public class QuizController {

    @GetMapping("/start")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> startQuizResolving() {
        return ResponseEntity.ok(Translator.translate(MessagesEnum.BAD_EMAIL));
    }
}
