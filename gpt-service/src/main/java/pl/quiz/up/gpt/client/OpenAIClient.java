package pl.quiz.up.gpt.client;

import pl.quiz.up.gpt.dto.request.ChatGPTRequest;
import pl.quiz.up.gpt.dto.request.WhisperTranscriptionRequest;
import pl.quiz.up.gpt.dto.response.ChatGPTResponse;
import pl.quiz.up.gpt.dto.response.WhisperTranscriptionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "openai-service",
        url = "${openai-service.urls.base-url-v1}",
        configuration = OpenAIClientConfig.class
)
public interface OpenAIClient {

    @PostMapping(value = "${openai-service.urls.chat-url-v1}", headers = {"Content-Type=application/json"})
    ChatGPTResponse chat(@RequestBody ChatGPTRequest chatGPTRequest);

    @PostMapping(value = "${openai-service.urls.create-transcription-url-v1}", headers = {"Content-Type=multipart/form-data"})
    WhisperTranscriptionResponse createTranscription(@ModelAttribute WhisperTranscriptionRequest whisperTranscriptionRequest);
}
