package pl.quiz.up.quiz.mapper;

import org.modelmapper.PropertyMap;
import pl.quiz.up.quiz.mapper.converter.QuizAnswerEntitySetToAnswerDtoSet;
import pl.quiz.up.quiz.dto.response.QuestionDto;
import pl.quiz.up.quiz.entity.QuizQuestionEntity;

import java.util.Base64;

public class QuizQuestionEntityToQuestionDto extends PropertyMap<QuizQuestionEntity, QuestionDto> {


    @Override
    protected void configure() {
        map().setQuestionId(source.getQuestionId());
        map().setQuestion(source.getQuestion());
        skip().setImage(null);
        using(new QuizAnswerEntitySetToAnswerDtoSet()).map(source.getQuizAnswerEntities()).setAnswerDtoSet(null);
    }
}
