package pl.quiz.up.auth.controller;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.auth.dto.AuthRequest;
import pl.quiz.up.auth.dto.EmailValidationRequestDto;
import pl.quiz.up.auth.dto.RegisterRequest;
import pl.quiz.up.auth.dto.UserNameValidationRequestDto;
import pl.quiz.up.auth.service.UserInfoService;
import pl.quiz.up.common.entity.UserInfo;
import pl.quiz.up.common.exception.dto.ValidationErrorList;
import pl.quiz.up.common.mapper.DTO;
import pl.quiz.up.common.messages.MessagesEnum;
import pl.quiz.up.common.messages.Translator;
import pl.quiz.up.common.service.JwtService;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;


    @ApiResponse(headers = @Header(name = HttpHeaders.AUTHORIZATION))
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        log.info("Started execution authenticate method");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtService.generateToken(authRequest.getEmail())).build();
        } else {
            ValidationErrorList body = ValidationErrorList.of(AuthRequest.Fields.password, Translator.translate(MessagesEnum.INVALID_EMAIL_OR_PASSWORD));
            return body.createResponseEntity();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@DTO(RegisterRequest.class) UserInfo userInfo) {
        userInfoService.save(userInfo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/email/validate")
    public ResponseEntity<?> validateEmail(@Valid @RequestBody EmailValidationRequestDto dto) {
        if (userInfoService.doEmailExists(dto.getEmail())) {
            String fieldName = EmailValidationRequestDto.Fields.email;
            ValidationErrorList body = ValidationErrorList.of(fieldName, Translator.translate(MessagesEnum.EXISTS_EMAIL));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register/username/validate")
    public ResponseEntity<?> validateEmail(@RequestBody UserNameValidationRequestDto dto) {
        if (userInfoService.doUserNameExists(dto.getUserName())) {
            String fieldName = UserNameValidationRequestDto.Fields.userName;
            ValidationErrorList body = ValidationErrorList.of(fieldName, Translator.translate(MessagesEnum.EXISTS_USER_NAME));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
        return ResponseEntity.ok().build();
    }
}
