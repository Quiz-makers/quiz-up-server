package pl.quiz.up.quiz.mapper;

import org.modelmapper.PropertyMap;
import pl.quiz.up.quiz.dto.response.CategoriesDto;
import pl.quiz.up.quiz.entity.QuizCategoryEntity;

public class QuizCategoryEntityToCategoriesDto extends PropertyMap<QuizCategoryEntity, CategoriesDto> {

    @Override
    protected void configure() {
        map().setCategoryId(source.getCategoryId());
        map().setCategory(source.getCategory());
        map().setThumbnail(source.getThumbnail());
    }
}
