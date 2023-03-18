package pl.quiz.up.auth.dto;

import java.util.Set;

public record ValidationErrorDto(Set<ErrorDto> errors) {

}
