package pl.quiz.up.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.auth.messages.Messages;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String email;

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String password;
}
