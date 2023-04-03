package pl.quiz.up.quiz.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.entity.QuizEntity;

public class StartQuizDtoMapper {

    private final ModelMapper mapper = new ModelMapper();

//    private void setup() {
//        this.m
//    }

//    public StartQuizDto map(QuizEntity entity) {
//        TypeMap<QuizEntity, StartQuizDto> propertyMapper = this.mapper.createTypeMap(QuizEntity.class, StartQuizDto.class);
//        propertyMapper.addMappings(mapper -> {
//            mapper.map(src -> src.getFirstName() + " " + src.getLastName(), PersonDTO::setFullName);
//        });
//    }
}
