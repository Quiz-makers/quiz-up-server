package pl.quiz.up.gpt.controller;

import pl.quiz.up.common.annotation.UserAuthority;
import pl.quiz.up.gpt.dto.request.TranscriptionRequest;
import pl.quiz.up.gpt.dto.response.ChatGPTResponse;
import pl.quiz.up.gpt.dto.request.ChatRequest;
import pl.quiz.up.gpt.dto.response.WhisperTranscriptionResponse;
import pl.quiz.up.gpt.service.OpenAIClientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class OpenAIClientController {

    private final OpenAIClientService openAIClientService;

    @UserAuthority
    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatGPTResponse chat(@RequestBody ChatRequest chatRequest){
        return openAIClientService.chat(chatRequest);
    }

    @UserAuthority
    @PostMapping(value = "/transcription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WhisperTranscriptionResponse createTranscription(@ModelAttribute TranscriptionRequest transcriptionRequest){
        return openAIClientService.createTranscription(transcriptionRequest);
    }
}
