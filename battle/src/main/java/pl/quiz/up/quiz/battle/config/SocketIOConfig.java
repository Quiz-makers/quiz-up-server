package pl.quiz.up.quiz.battle.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.quiz.up.quiz.battle.listener.AuthorizationListenerImpl;

@Configuration
@RequiredArgsConstructor
public class SocketIOConfig {

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    private final AuthorizationListenerImpl authorizationListener;
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setAuthorizationListener(authorizationListener);
        return new SocketIOServer(config);
    }

}
