package pl.quiz.up.gpt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.quiz.up.gpt.dto.request.Message;
import lombok.Data;

import java.io.Serializable;

@Data
public class Choice implements Serializable {
    private Integer index;
    private Message message;
    @JsonProperty("finish_reason")
    private String finishReason;
}
