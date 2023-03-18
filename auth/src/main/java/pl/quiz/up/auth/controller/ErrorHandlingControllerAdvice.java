package pl.quiz.up.auth.controller;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.quiz.up.auth.dto.ErrorDto;
import pl.quiz.up.auth.dto.ValidationErrorDto;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    private final MessageSource messageSource;

    public ErrorHandlingControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapToErrorDto)
                .collect(Collectors.toSet()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorDto);
    }

    private ErrorDto mapToErrorDto(ObjectError error) {
        FieldError fieldError = (FieldError) error;
        return new ErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
