package pl.quiz.up.gpt.service;

import pl.quiz.up.gpt.dto.request.*;
import pl.quiz.up.gpt.dto.response.Choice;
import pl.quiz.up.gpt.exception.GPTServiceException;
import pl.quiz.up.gpt.gpttemplates.GptTemplates;
import pl.quiz.up.gpt.client.OpenAIClient;
import pl.quiz.up.gpt.client.OpenAIClientConfig;
import pl.quiz.up.gpt.dto.response.ChatGPTResponse;
import pl.quiz.up.gpt.dto.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

        return openAIClient.chat(buildGPTRequest(message));
    }

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }

    public String generateQuizFromTitle(QuizFromTitleDto dto) {
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(GptTemplates
                        .getTemplateForQuizFromTitleQuery(
                                dto.getTitle(),
                                dto.getCategory(),
                                dto.getNumberOfQuestions(),
                                dto.getAnswersPerQuestion()))
                .build();

        Message resp = openAIClient.chat(buildGPTRequest(message))
                .getChoices().stream()
                    .findFirst()
                    .map(Choice::getMessage)
                    .orElseThrow(() ->
                            new GPTServiceException(String.format("GPT service response timeout for quiz '%s'", dto.getTitle())));

        return resp.getContent();
    }

    public String generateQuizFromText(QuizFromTextDto dto) {
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(GptTemplates
                        .getTemplateForQuizFromTextQuery(
                                dto.getText(),
                                dto.getCategory(),
                                dto.getNumberOfQuestions(),
                                dto.getAnswersPerQuestion()))
                .build();

        Message resp = openAIClient.chat(buildGPTRequest(message))
                .getChoices().stream()
                    .findFirst()
                    .map(Choice::getMessage)
                    .orElseThrow(() ->
                            new GPTServiceException("Failed to generate quiz from given text. Please try again in a while."));

        return resp.getContent();
    }

    private ChatGPTRequest buildGPTRequest(Message message) {
        return ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(Collections.singletonList(message))
                .build();
    }

}
