package pl.quiz.up.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.up.auth.dto.AuthRequest;
import pl.quiz.up.auth.dto.RegisterRequest;
import pl.quiz.up.auth.entity.UserInfo;
import pl.quiz.up.auth.mapper.DTO;
import pl.quiz.up.auth.service.JwtService;
import pl.quiz.up.auth.service.UserInfoService;

@RestController("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoService userInfoService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, UserInfoService userInfoService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userInfoService = userInfoService;
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
}
