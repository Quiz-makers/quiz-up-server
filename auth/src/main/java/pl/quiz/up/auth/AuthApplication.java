package pl.quiz.up.auth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("pl.quiz.up.auth.*")
@ComponentScan(basePackages = {"pl.quiz.up.auth.*"})
@EntityScan("pl.quiz.up.auth.*")
@OpenAPIDefinition(
    servers = {
        @Server(url = "http://localhost:8081/auth", description = "[LOCAL env] API Gateway service auth service endpoint"),
        @Server(url = "http://localhost:8181/auth", description = "[LOCAL env] Auth service direct endpoint (Default: DISABLED)")})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
