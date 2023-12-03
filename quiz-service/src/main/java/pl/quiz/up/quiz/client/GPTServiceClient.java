package pl.quiz.up.quiz.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.quiz.up.quiz.dto.request.QuizFromTextGenerationDto;
import pl.quiz.up.quiz.dto.request.QuizFromTitleGenerationDto;

@FeignClient(
        name = "qpt-service",
        url = "${gpt-service.urls.base-url}",
        configuration = GPTServiceClientConfig.class
)
public interface GPTServiceClient {

    @PostMapping(
            value = "${gpt-service.urls.generate-by-title}",
            headers = {"Content-Type=application/json"})
    ResponseEntity<String> generateQuizFromTitle(@RequestBody @Valid QuizFromTitleGenerationDto dto);

    @PostMapping(
            value = "${gpt-service.urls.generate-by-text}",
            headers = {"Content-Type=application/json"})
    ResponseEntity<String> generateQuizFromText(@RequestBody @Valid QuizFromTextGenerationDto dto);

}
