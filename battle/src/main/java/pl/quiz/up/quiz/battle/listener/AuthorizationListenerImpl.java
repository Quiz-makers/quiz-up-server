package pl.quiz.up.quiz.battle.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.quiz.up.common.config.UserInfoUserDetailsService;
import pl.quiz.up.common.service.JwtService;

@Component
@RequiredArgsConstructor
public class AuthorizationListenerImpl implements AuthorizationListener {
    private final JwtService jwtService;
    private final UserInfoUserDetailsService userDetailsService;

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        String jwtToken = handshakeData.getHttpHeaders().get(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
            String username = jwtService.extractUsername(jwtToken);
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return jwtService.validateToken(jwtToken, userDetails);
            }
        }
        return Boolean.FALSE;
    }
}
