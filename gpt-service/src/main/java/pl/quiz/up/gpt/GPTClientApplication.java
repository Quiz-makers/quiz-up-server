package pl.quiz.up.gpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GPTClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GPTClientApplication.class, args);
	}

}
