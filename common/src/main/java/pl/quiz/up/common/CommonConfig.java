package pl.quiz.up.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"pl.quiz.up.common.*"})
@EnableJpaRepositories(basePackages = {"pl.quiz.up.common.*"})
@EntityScan(basePackages = {"pl.quiz.up.common.*"})
public class CommonConfig {
}
