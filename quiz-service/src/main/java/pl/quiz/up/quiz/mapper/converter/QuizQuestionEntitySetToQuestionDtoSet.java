package pl.quiz.up.quiz.mapper.converter;

import org.modelmapper.AbstractConverter;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.response.QuestionDto;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class QuizQuestionEntitySetToQuestionDtoSet extends AbstractConverter<Set<QuizQuestionEntity>, Set<QuestionDto>> {
    @Override
    protected Set<QuestionDto> convert(Set<QuizQuestionEntity> quizQuestionEntities) {
        return quizQuestionEntities.stream()
                .map(item -> ModelMapperConfig.modelMapper.map(item, QuestionDto.class))
                .collect(Collectors.toSet());
    }
}
