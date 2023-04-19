package pl.quiz.up.quiz.mapper.converter;

import org.modelmapper.AbstractConverter;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.response.QuestionDto;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizQuestionEntitySetToQuestionDtoSet extends AbstractConverter<List<QuizQuestionEntity>, Set<QuestionDto>> {
    @Override
    protected Set<QuestionDto> convert(List<QuizQuestionEntity> quizQuestionEntities) {
        return quizQuestionEntities.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    QuestionDto map(QuizQuestionEntity source) {
        return QuestionDto.builder()
                .questionId(source.getQuestionId())
                .question(source.getQuestion())
                .image(Objects.isNull(source.getQuestionImage()) ? null : Base64.getEncoder().encodeToString(source.getQuestionImage()))
                .answerDtoSet(new QuizAnswerEntitySetToAnswerDtoSet().convert(source.getQuizAnswerEntities()))
                .build();
    }

}
