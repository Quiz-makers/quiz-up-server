package pl.quiz.up.gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.gpt.dto.request.QuizFromTextDto;
import pl.quiz.up.gpt.dto.request.QuizFromTitleDto;
import pl.quiz.up.gpt.service.OpenAIClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quiz/generate")
public class V1QuizGeneratorController {

    private final OpenAIClientService openAIClientService;

    @UserAuthority
    @PostMapping(value = "/fromTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateQuizFromTitle(@RequestBody QuizFromTitleDto dto) {
        return ResponseEntity.ok(openAIClientService.generateQuizFromTitle(dto));
    }

    @UserAuthority
    @PostMapping(value = "/fromText", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateQuizFromText(@RequestBody QuizFromTextDto dto) {
        return ResponseEntity.ok(openAIClientService.generateQuizFromText(dto));
    }
}
