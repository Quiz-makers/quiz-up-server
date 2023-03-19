package pl.quiz.up.auth.controller;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.auth.dto.*;
import pl.quiz.up.auth.entity.UserInfo;
import pl.quiz.up.auth.mapper.DTO;
import pl.quiz.up.auth.service.JwtService;
import pl.quiz.up.auth.service.UserInfoService;
import pl.quiz.up.auth.utils.Messages;
import pl.quiz.up.auth.utils.MessagesEnum;
import pl.quiz.up.auth.utils.Translator;

import java.util.Set;

@RestController("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;
    private final MessageSource messageSource;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserInfoService userInfoService, MessageSource messageSource) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userInfoService = userInfoService;
        this.messageSource = messageSource;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtService.generateToken(authRequest.getEmail())).build();
        } else {
            throw new UsernameNotFoundException("invalid user request !");
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
            ErrorDto errorDto = new ErrorDto(fieldName, Translator.translate(MessagesEnum.EXISTS_EMAIL));
            ValidationErrorDto body = new ValidationErrorDto(Set.of(errorDto));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
        return ResponseEntity.ok().build();
    }
}
