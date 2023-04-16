package pl.quiz.up.quiz.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.common.utils.AuthenticationUtils;
import pl.quiz.up.quiz.config.ModelMapperConfig;
import pl.quiz.up.quiz.dto.request.FinishQuizAnswerDto;
import pl.quiz.up.quiz.dto.request.FinishQuizDto;
import pl.quiz.up.quiz.dto.response.AnswerDto;
import pl.quiz.up.quiz.dto.response.QuestionDto;
import pl.quiz.up.quiz.dto.response.QuizResultDto;
import pl.quiz.up.quiz.dto.response.StartQuizDto;
import pl.quiz.up.quiz.entity.*;
import pl.quiz.up.quiz.exception.NotFoundException;
import pl.quiz.up.quiz.mapper.converter.QuizAnswerEntitySetToAnswerDtoSet;
import pl.quiz.up.quiz.repository.facade.QuizAnswerRepository;
import pl.quiz.up.quiz.repository.facade.QuizRepository;
import pl.quiz.up.quiz.repository.facade.TakeAnswerRepository;
import pl.quiz.up.quiz.repository.facade.TakeRepository;
import pl.quiz.up.quiz.utils.Constants;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;
    private final QuizAnswerRepository answerRepository;
    private final TakeAnswerRepository takeAnswerRepository;
    private final ModelMapper modelMapper;

    public StartQuizDto startQuiz(long id) {
        return quizRepository.findByQuizId(id)
                .map(item -> modelMapper.map(item, StartQuizDto.class))
                .orElseThrow(() -> new NotFoundException("Quiz not found with id: " + id));
    }

    @Transactional
    public QuizResultDto finishQuiz(FinishQuizDto finishQuizDto) {
        TakeEntity takeEntity = createTakeEntity(finishQuizDto);

        List<TakeAnswerEntity> answerEntityList = new ArrayList<>();
        for (FinishQuizAnswerDto answerDto : finishQuizDto.getAnswerDtoList()) {
            takeEntity.setScoreResult((short) (takeEntity.getScoreResult() + getPoints(answerDto.getAnswerId()))) ;
            TakeAnswerEntity takeAnswerEntity = createTakeAnswerEntity(takeEntity, answerDto);
            answerEntityList.add(takeAnswerEntity);
        }
        takeAnswerRepository.saveAll(answerEntityList);
        return new QuizResultDto(takeEntity.getScoreResult(), finishQuizDto.getAnswerDtoList().size());
    }

    private static TakeAnswerEntity createTakeAnswerEntity(TakeEntity takeEntity, FinishQuizAnswerDto answerDto) {
        return TakeAnswerEntity.builder()
                .takeEntity(takeEntity)
                .questionId(answerDto.getQuestionId())
                .answerId(answerDto.getAnswerId())
                .mostActualAnswer(Boolean.TRUE)
                .build();
    }

    private static TakeEntity createTakeEntity(FinishQuizDto finishQuizDto) {
        return TakeEntity.builder()
                .userId(AuthenticationUtils.getUserId())
                .quizId(finishQuizDto.getQuizId())
                .startedAt(finishQuizDto.getStartTime())
                .finishedAt(finishQuizDto.getFinishTime())
                .scoreResult(Constants.SHORT_ZERO)
                .build();
    }

    private short getPoints(long answerId) {
        QuizAnswerEntity answerEntity = answerRepository.findById(answerId)
                .orElseThrow(NotFoundException::new);
        return answerEntity.getCorrect() ? Constants.SHORT_ONE : Constants.SHORT_ZERO;
    }

}
