package pl.quiz.up.quiz.mapper

import org.junit.jupiter.api.extension.ExtendWith
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.quiz.up.quiz.config.ModelMapperConfig
import pl.quiz.up.quiz.dto.response.AnswerDto
import pl.quiz.up.quiz.dto.response.QuestionDto
import pl.quiz.up.quiz.dto.response.StartQuizDto
import pl.quiz.up.quiz.entity.QuizAnswerEntity
import pl.quiz.up.quiz.entity.QuizEntity
import pl.quiz.up.quiz.entity.QuizQuestionEntity
import spock.lang.Specification

@ContextConfiguration(loader = SpringBootContextLoader.class, classes = [ModelMapperConfig.class])
class QuizEntityToStartQuizSpec extends Specification {

    @Autowired
    private ModelMapper modelMapper

    def "when QuizEntity is filled, then map to StartQuizDto"(){
        given:
            def quizAnswerEntity = QuizAnswerEntity.builder()
                .answerId(1L)
                .answer("No")
                .build()

            def quizQuestionEntity = QuizQuestionEntity.builder()
                .questionId(1L)
                .question("You talking to me?")
                .quizAnswerEntities(Set.of(quizAnswerEntity))
                .build()

            def quizEntity = QuizEntity.builder()
                .quizId(1L)
                .title("Nice title")
                .description("Really long description")
                .quizQuestionEntities(Set.of(quizQuestionEntity))
                .build()

            def correctAnswerDto = AnswerDto.builder()
                .id(1L)
                .answer("No")
                .build()

            def correctQuestionDto = QuestionDto.builder()
                .questionId(1L)
                .question("You talking to me?")
                .answerDtoSet(Set.of(correctAnswerDto))
                .build()

            def correctStartQuizDto = StartQuizDto.builder()
                .quizId(1L)
                .title("Nice title")
                .description("Really long description")
                .questionDtoSet(Set.of(correctQuestionDto))
                .build()

        when:
            StartQuizDto startQuizDto = modelMapper.map(quizEntity, StartQuizDto.class)
        then:
            startQuizDto == correctStartQuizDto
    }



}
