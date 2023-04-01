package pl.quiz.up.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import pl.quiz.up.common.messages.Messages;

@FieldNameConstants
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String email;

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String password;
}
