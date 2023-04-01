package pl.quiz.up.common.exception;

import lombok.Getter;
import pl.quiz.up.common.exception.dto.ValidationErrorList;


public class ValidationException extends RuntimeException {

    @Getter
    private final ValidationErrorList validationErrorList;

    public ValidationException(ValidationErrorList validationErrorList) {
        this.validationErrorList = validationErrorList;
    }

}
