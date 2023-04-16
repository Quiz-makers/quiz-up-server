package pl.quiz.up.quiz.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.quiz.up.quiz.mapper.*;

@Component
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        modelMapper.addMappings(new QuizEntityToStartQuizDto());
        modelMapper.addMappings(new QuizCategoryEntityToCategoriesDto());
        modelMapper.addMappings(new QuizTypeEntityToQuizTypesDto());
        return modelMapper;
    }



}
