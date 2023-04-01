package pl.quiz.up.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.quiz.up.common.entity.UserInfo;
import pl.quiz.up.common.exception.dto.ValidationErrorList;
import pl.quiz.up.common.messages.MessagesEnum;
import pl.quiz.up.common.service.repository.UserInfoRepository;

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
        ValidationErrorList validationErrorList = ValidationErrorList.empty();
        if (doEmailExists(userInfo.getEmail()))
            validationErrorList.add(UserInfo.Fields.email, MessagesEnum.EXISTS_EMAIL);
        if (doUserNameExists(userInfo.getUserName()))
            validationErrorList.add(UserInfo.Fields.userName, MessagesEnum.EXISTS_USER_NAME);
        validationErrorList.throwIfNotEmpty();
    }

    public boolean doEmailExists(String email) {
        return repository.existsByEmail(email);
    }

    public boolean doUserNameExists(String userName) {
        return repository.existsByUserName(userName);
    }
}
