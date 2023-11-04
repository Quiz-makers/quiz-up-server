package pl.quiz.up.quiz.battle.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.quiz.up.common.config.UserInfoUserDetailsService;
import pl.quiz.up.common.service.JwtService;
import pl.quiz.up.quiz.battle.dto.server.ServerMessage;
import pl.quiz.up.quiz.battle.engine.BattleEngine;
import pl.quiz.up.quiz.battle.enums.ServerInfo;

import java.util.Objects;

import static pl.quiz.up.quiz.battle.utils.Constants.EVENT_SERVER_MESSAGE;
import static pl.quiz.up.quiz.battle.utils.Constants.USER_DETAILS;


@Component
@RequiredArgsConstructor
public class ConnectListenerImpl implements ConnectListener {

    private final JwtService jwtService;
    private final UserInfoUserDetailsService userDetailsService;
    private final BattleEngine battleEngine;


    @Override
    public void onConnect(SocketIOClient client) {
        String jwtToken = client.getHandshakeData().getHttpHeaders().get(HttpHeaders.AUTHORIZATION);
        jwtToken = jwtToken.substring(7);
        String username = jwtService.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        for (SocketIOClient loggedClient : client.getNamespace().getAllClients()) {
            if (Objects.nonNull(loggedClient.get(USER_DETAILS)) && loggedClient.get(USER_DETAILS).equals(userDetails)) {
                client.sendEvent(EVENT_SERVER_MESSAGE, ServerMessage.of(ServerInfo.ERROR_MESSAGE, "You are currently logged id"));
                client.disconnect();
                return;
            }
        }
        client.set(USER_DETAILS, userDetails);
        battleEngine.addToWaitingRoom(client);
    }

}
