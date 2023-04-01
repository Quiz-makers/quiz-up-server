package pl.quiz.up.quiz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.common.annotation.UserAuthority;


@RestController
public class QuizController {

    @GetMapping("/start")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> startQuizResolving() {
        return ResponseEntity.ok().build();
    }
}
