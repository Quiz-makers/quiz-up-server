package pl.quiz.up.quiz.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.mapper.*;

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
        modelMapper.addMappings(new QuizCategoryEntityToCategoriesDto());
        modelMapper.addMappings(new QuizTypeEntityToQuizTypesDto());
    }

}
