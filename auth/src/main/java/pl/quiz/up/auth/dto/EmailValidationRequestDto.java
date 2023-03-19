package pl.quiz.up.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import pl.quiz.up.auth.utils.Messages;

@FieldNameConstants
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationRequestDto {
    @Email(message = Messages.BAD_EMAIL)
    private String email;
}
