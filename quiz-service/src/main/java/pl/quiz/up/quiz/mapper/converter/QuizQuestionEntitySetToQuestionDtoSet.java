package pl.quiz.up.quiz.mapper.converter;

import org.modelmapper.AbstractConverter;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.response.QuestionDto;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizQuestionEntitySetToQuestionDtoSet extends AbstractConverter<Set<QuizQuestionEntity>, Set<QuestionDto>> {
    @Override
    protected Set<QuestionDto> convert(Set<QuizQuestionEntity> quizQuestionEntities) {
        return quizQuestionEntities.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    QuestionDto map(QuizQuestionEntity source) {
        return QuestionDto.builder()
                .questionId(source.getQuestionId())
                .question(source.getQuestion())
                .image(Base64.getEncoder().encodeToString(source.getQuestionImage()))
                .answerDtoSet(new QuizAnswerEntitySetToAnswerDtoSet().convert(source.getQuizAnswerEntities()))
                .build();
    }

}
