package pl.quiz.up.auth.dto;

import java.util.Set;

public record ValidationErrorsDto(Set<ErrorDto> errors) {

}
