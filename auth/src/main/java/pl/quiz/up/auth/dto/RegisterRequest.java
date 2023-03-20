package pl.quiz.up.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.up.auth.messages.Messages;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = Messages.EMPTY_FIELD)
    private String name;
    @NotBlank(message = Messages.EMPTY_FIELD)
    private String surname;
    @NotBlank(message = Messages.EMPTY_FIELD)
    private String userName;
    @Email(message = Messages.BAD_EMAIL)
    private String email;
    @NotBlank(message = Messages.EMPTY_FIELD)
    private String password;
}
