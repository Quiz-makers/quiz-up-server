package pl.quiz.up.quiz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"pl.quiz.up.quiz.*", "pl.quiz.up.common.*"})
@EntityScan(basePackages = {"pl.quiz.up.quiz.*", "pl.quiz.up.common.*"})
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8081/quizapp", description = "[LOCAL env] API Gateway quiz service endpoint"),
                @Server(url = "http://localhost:8290/quizapp", description = "[LOCAL env] Quiz service direct endpoint (Default: DISABLED)")})
public class QuizApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }
}
