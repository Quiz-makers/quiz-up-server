package pl.quiz.up.quiz.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.quiz.utils.Constants;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishQuizDto {
    private Long quizId;
    private List<FinishQuizAnswerDto> answerDtoList;

    @JsonFormat(pattern = Constants.LOCAL_DATE_TIME_PATTERN)
    private LocalDateTime startTime;
    @JsonFormat(pattern = Constants.LOCAL_DATE_TIME_PATTERN)
    private LocalDateTime finishTime;
}
