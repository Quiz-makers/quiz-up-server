package pl.quiz.up.quiz.battle.dto.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class BattleResultResponse {
    List<QuestionResult> questionResults;

    public BattleResultResponse() {
        this.questionResults = new LinkedList<>();
    }

    public void addQuestionResult(QuestionResult result) {
        this.questionResults.add(result);
    }
}
