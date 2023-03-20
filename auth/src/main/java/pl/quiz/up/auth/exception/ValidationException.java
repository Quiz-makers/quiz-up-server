package pl.quiz.up.auth.exception;

import lombok.Getter;
import pl.quiz.up.auth.dto.ErrorDto;
import pl.quiz.up.auth.dto.ValidationErrorsDto;
import pl.quiz.up.auth.messages.Translator;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

    @Getter
    private final ValidationErrorsDto validationErrorsDto;

    public ValidationException(Set<ValidationErrorDto> validationErrorDtos) {
        Set<ErrorDto> errorDtos = validationErrorDtos.stream()
                .map(this::mapMessageFieldDto)
                .collect(Collectors.toSet());
        this.validationErrorsDto = new ValidationErrorsDto(errorDtos);
    }

    private ErrorDto mapMessageFieldDto(ValidationErrorDto messageFieldDto) {
        return new ErrorDto(messageFieldDto.fieldName(), Translator.translate(messageFieldDto.messagesEnum()));
    }
}
