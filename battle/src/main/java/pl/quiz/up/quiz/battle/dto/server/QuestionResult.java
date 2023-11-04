package pl.quiz.up.quiz.battle.dto.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class QuestionResult {
    private Map<String, Result> resultList;

    @Data
    @AllArgsConstructor
    public static class Result {
        private long questionId;
        private Long answerId;
        private Boolean correct;
    }
}
