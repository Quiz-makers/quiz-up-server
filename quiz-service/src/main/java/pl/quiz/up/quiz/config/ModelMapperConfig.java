package pl.quiz.up.quiz.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.mapper.QuizAnswerEntityToAnswerDto;
import pl.quiz.up.quiz.mapper.QuizQuestionEntityToQuestionDto;
import pl.quiz.up.quiz.mapper.QuizEntityToStartQuizDto;

@Component
public class ModelMapperConfig {

    public static ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper modelMapper() {
        setup();
        return modelMapper;
    }

    private void setup() {
        modelMapper.addMappings(new QuizAnswerEntityToAnswerDto());
        modelMapper.addMappings(new QuizQuestionEntityToQuestionDto());
        modelMapper.addMappings(new QuizEntityToStartQuizDto());
    }

}
