package pl.quiz.up.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.auth.entity.UserInfo;
import pl.quiz.up.auth.exception.ValidationErrorDto;
import pl.quiz.up.auth.exception.ValidationException;
import pl.quiz.up.auth.service.repository.UserInfoRepository;
import pl.quiz.up.auth.messages.MessagesEnum;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserInfoService {
    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserInfo save(UserInfo userInfo) {
        validate(userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setDefaultRole();
        return repository.save(userInfo);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void validate(UserInfo userInfo) {
        Set<ValidationErrorDto> validationErrorDtos = new HashSet<>();
        if (doEmailExists(userInfo.getEmail()))
            validationErrorDtos.add(new ValidationErrorDto(UserInfo.Fields.email, MessagesEnum.EXISTS_EMAIL));
        if(doUserNameExists(userInfo.getUserName()))
            validationErrorDtos.add(new ValidationErrorDto(UserInfo.Fields.userName, MessagesEnum.EXISTS_USER_NAME));
        if(!validationErrorDtos.isEmpty())
            throw new ValidationException(validationErrorDtos);
    }

    public boolean doEmailExists(String email) {
        return repository.existsByEmail(email);
    }
    public boolean doUserNameExists(String userName) {
        return repository.existsByUserName(userName);
    }
}
