package pl.quiz.up.gpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.quiz.up.gpt.dto.request.ChatRequest;
import pl.quiz.up.gpt.dto.response.ChatGPTResponse;
import pl.quiz.up.gpt.service.OpenAIClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/quiz/generate")
public class OpenAIQuizGeneratorV1Controller {

    private final OpenAIClientService openAIClientService;

    @PostMapping(value = "/fromTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse generateQuizFromTitle(@RequestBody ChatRequest chatRequest){
        return openAIClientService.chat(chatRequest);
    }

    @PostMapping(value = "/fromText", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse generateQuizFromText(@RequestBody ChatRequest chatRequest){
        return openAIClientService.chat(chatRequest);
    }
}
