package pl.quiz.up.auth.controller;

import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.quiz.up.auth.dto.ErrorDto;
import pl.quiz.up.auth.dto.ValidationErrorsDto;
import pl.quiz.up.auth.exception.NotFoundMessage;
import pl.quiz.up.auth.exception.ValidationException;

import java.util.stream.Collectors;

@ControllerAdvice
@NoArgsConstructor
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorsDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapToErrorDto)
                .collect(Collectors.toSet()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsDto);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<NotFoundMessage> onUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundMessage(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ValidationErrorsDto> onMethodArgumentNotValidException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getValidationErrorsDto());
    }

    private ErrorDto mapToErrorDto(ObjectError error) {
        FieldError fieldError = (FieldError) error;
        return new ErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
