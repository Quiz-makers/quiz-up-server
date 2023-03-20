package pl.quiz.up.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldNameConstants
public class UserNameValidationRequestDto {
    private String userName;
}
