package pl.quiz.up.gpt.service;

import pl.quiz.up.gpt.openaiclient.OpenAIClient;
import pl.quiz.up.gpt.openaiclient.OpenAIClientConfig;
import pl.quiz.up.gpt.dto.request.ChatGPTRequest;
import pl.quiz.up.gpt.dto.request.WhisperTranscriptionRequest;
import pl.quiz.up.gpt.dto.request.TranscriptionRequest;
import pl.quiz.up.gpt.dto.response.ChatGPTResponse;
import pl.quiz.up.gpt.dto.request.ChatRequest;
import pl.quiz.up.gpt.dto.request.Message;
import pl.quiz.up.gpt.dto.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OpenAIClientService {

    private final OpenAIClient openAIClient;

    private final OpenAIClientConfig openAIClientConfig;

    private final static String ROLE_USER = "user";

    public ChatGPTResponse chat(ChatRequest chatRequest){
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(chatRequest.getQuestion())
                .build();
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(Collections.singletonList(message))
                .build();
        return openAIClient.chat(chatGPTRequest);
    }

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }
}
