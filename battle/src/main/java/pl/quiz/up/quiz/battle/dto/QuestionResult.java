package pl.quiz.up.quiz.battle.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionResult {
    private List<Result> resultList;

    @Data
    @AllArgsConstructor
    public static class Result{
        private String userName;
        private Boolean correct;
    }
}
