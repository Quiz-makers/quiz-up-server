package pl.quiz.up.quiz.mapper;

import org.modelmapper.PropertyMap;
import pl.quiz.up.quiz.dto.response.QuizTypesDto;
import pl.quiz.up.quiz.entity.QuizTypeEntity;

public class QuizTypeEntityToQuizTypesDto extends PropertyMap<QuizTypeEntity, QuizTypesDto> {

    @Override
    protected void configure() {
        map().setTypeId(source.getTypeId());
        map().setType(source.getType());
    }
}
