package pl.quiz.up.quiz.mapper.converter;

import org.modelmapper.AbstractConverter;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.response.AnswerDto;
import pl.quiz.up.quiz.entity.QuizAnswerEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizAnswerEntitySetToAnswerDtoSet extends AbstractConverter<List<QuizAnswerEntity>, List<AnswerDto>> {


    @Override
    protected List<AnswerDto> convert(List<QuizAnswerEntity> quizAnswerEntities) {
        return quizAnswerEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private AnswerDto map(QuizAnswerEntity source) {
        return AnswerDto.builder()
                .id(source.getAnswerId())
                .answer(source.getAnswer())
                .isCorrect(source.getCorrect())
                .build();
    }
}
