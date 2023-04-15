package pl.quiz.up.quiz.mapper;

import org.modelmapper.PropertyMap;
import pl.quiz.up.quiz.dto.response.AnswerDto;
import pl.quiz.up.quiz.entity.QuizAnswerEntity;

public class QuizAnswerEntityToAnswerDto extends PropertyMap<QuizAnswerEntity, AnswerDto> {
    @Override
    protected void configure() {
        map().setId(source.getAnswerId());
        map().setAnswer(source.getAnswer());
        map().setIsCorrect(source.getCorrect());
    }
}
