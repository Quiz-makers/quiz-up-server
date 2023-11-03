package pl.quiz.up.quiz.battle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"pl.quiz.up.quiz.battle.*", "pl.quiz.up.common.*"})
@EntityScan(basePackages = {"pl.quiz.up.quiz.battle.*", "pl.quiz.up.common.*"})
@EnableScheduling
public class BattleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BattleApplication.class, args);
    }

}
