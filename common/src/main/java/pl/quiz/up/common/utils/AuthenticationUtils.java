package pl.quiz.up.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.quiz.up.common.config.UserInfoUserDetails;

@UtilityClass
public class AuthenticationUtils {

    public String getUserEmail() {
        return ((UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Long getUserId() {
        return ((UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
