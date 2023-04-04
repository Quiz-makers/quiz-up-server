package pl.quiz.up.quiz.mapper;

import org.modelmapper.PropertyMap;
import pl.quiz.up.quiz.mapper.converter.QuizQuestionEntitySetToQuestionDtoSet;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.entity.QuizEntity;

public class QuizEntityToStartQuizDto extends PropertyMap<QuizEntity, StartQuizDto> {

    @Override
    protected void configure() {
        map().setQuizId(source.getQuizId());
        map().setTitle(source.getTitle());
        map().setDescription(source.getDescription());
        using(new QuizQuestionEntitySetToQuestionDtoSet()).map(source.getQuizQuestionEntities()).setQuestionDtoSet(null);
    }
}
