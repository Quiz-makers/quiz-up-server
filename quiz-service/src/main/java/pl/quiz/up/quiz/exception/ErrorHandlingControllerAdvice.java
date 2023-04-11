package pl.quiz.up.quiz.exception;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.quiz.up.common.exception.ValidationException;
import pl.quiz.up.common.exception.dto.ErrorDto;
import pl.quiz.up.common.exception.dto.ValidationErrorList;
import pl.quiz.up.common.messages.MessagesEnum;
import pl.quiz.up.common.utils.AuthenticationUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@ControllerAdvice
@NoArgsConstructor
@Log4j2
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

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> onQuizNotFoundException(NotFoundException e, WebRequest request) {

        final String message = e.getMessage();
        log.warn("Captured QuizNotFoundException: " + message);

        ExceptionResponse response =
                new ExceptionResponse(
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), message, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalQuizOwnerIdException.class)
    ResponseEntity<Object> onIllegalQuizOwnerIdException(IllegalQuizOwnerIdException e, WebRequest request) {

        final String message = e.getMessage();
        log.warn("Captured IllegalQuizOwnerIdException: " + message + ". Requestor ID: " + AuthenticationUtils.getUserId());

        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), message, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuizTitleAlreadyExistsException.class)
    ResponseEntity<Object> onQuizTitleAlreadyExistsException(QuizTitleAlreadyExistsException e, WebRequest request) {

        final String message = e.getMessage();
        log.warn("Captured QuizTitleAlreadyExistsException: " + message);

        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), message, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Object> onDataIntegrityViolationException(DataIntegrityViolationException e, WebRequest request) {

        final String message =
                "Input data integrity violation due to integrity constraints. Please ensure that all required data is provided";

        log.warn("Captured DataIntegrityViolationException: " + e.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), message, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ValidationErrorList> onRuntimeException(RuntimeException e) {

        ValidationErrorList errorList =
                ValidationErrorList.of("message", MessagesEnum.INTERNAL_SERVER_ERROR);

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
