package pl.quiz.up.gpt.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatRequest implements Serializable {

    private String question;

}
