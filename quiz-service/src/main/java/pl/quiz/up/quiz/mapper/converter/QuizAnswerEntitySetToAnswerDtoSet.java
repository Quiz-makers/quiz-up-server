package pl.quiz.up.quiz.mapper.converter;

import org.modelmapper.AbstractConverter;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.response.AnswerDto;
import pl.quiz.up.quiz.entity.QuizAnswerEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class QuizAnswerEntitySetToAnswerDtoSet extends AbstractConverter<Set<QuizAnswerEntity>, Set<AnswerDto>> {


    @Override
    protected Set<AnswerDto> convert(Set<QuizAnswerEntity> quizAnswerEntities) {
        return quizAnswerEntities.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    private AnswerDto map(QuizAnswerEntity source) {
        return AnswerDto.builder()
                .id(source.getAnswerId())
                .answer(source.getAnswer())
                .isCorrect(source.getCorrect())
                .build();
    }
}
