package pl.quiz.up.quiz.battle.utils;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.experimental.UtilityClass;
import pl.quiz.up.common.config.UserInfoUserDetails;

import static pl.quiz.up.quiz.battle.utils.Constants.USER_DETAILS;

@UtilityClass
public class SocketClientUtils {

    public static UserInfoUserDetails getUserDetails(SocketIOClient client) {
        return client.get(USER_DETAILS);
    }

}
