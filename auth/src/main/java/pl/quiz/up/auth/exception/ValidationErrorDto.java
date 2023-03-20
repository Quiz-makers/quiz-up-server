package pl.quiz.up.auth.exception;

import pl.quiz.up.auth.messages.MessagesEnum;

public record ValidationErrorDto(String fieldName, MessagesEnum messagesEnum) {
}
