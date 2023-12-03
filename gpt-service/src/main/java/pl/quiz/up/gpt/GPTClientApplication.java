package pl.quiz.up.gpt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"pl.quiz.up.gpt.*", "pl.quiz.up.common.*"})
@EntityScan(basePackages = {"pl.quiz.up.gpt.*", "pl.quiz.up.common.*"})
@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost:8081/gptclient", description = "[LOCAL env] API Gateway GPT service endpoint"),
				@Server(url = "http://localhost:8095/gptclient", description = "[LOCAL env] GPT service direct endpoint (Default: DISABLED)")})
public class GPTClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GPTClientApplication.class, args);
	}

}
