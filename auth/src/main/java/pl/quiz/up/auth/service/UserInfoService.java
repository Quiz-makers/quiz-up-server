package pl.quiz.up.auth.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.quiz.up.auth.entity.UserInfo;
import pl.quiz.up.auth.service.repository.UserInfoRepository;

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
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setDefaultRole();
        return repository.save(userInfo);
    }
}
