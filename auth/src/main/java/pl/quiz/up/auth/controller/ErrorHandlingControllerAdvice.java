package pl.quiz.up.auth.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.quiz.up.common.exception.ValidationException;
import pl.quiz.up.common.exception.dto.ErrorDto;
import pl.quiz.up.common.exception.dto.ValidationErrorList;
import pl.quiz.up.common.messages.MessagesEnum;

import java.util.stream.Collectors;

@ControllerAdvice
@NoArgsConstructor
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorList> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorList validationErrorList = ValidationErrorList.of(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapToErrorDto)
                .collect(Collectors.toSet()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorList);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ValidationErrorList> onRuntimeException(RuntimeException e) {
        ValidationErrorList errorList = ValidationErrorList.of("message", MessagesEnum.INTERNAL_SERVER_ERROR);
        return errorList.createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ValidationErrorList> onValidationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getValidationErrorList());
    }

    private ErrorDto mapToErrorDto(ObjectError error) {
        FieldError fieldError = (FieldError) error;
        return ErrorDto.of(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
