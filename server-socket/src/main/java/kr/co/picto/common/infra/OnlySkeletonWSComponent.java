package kr.co.picto.common.infra;

import kr.co.picto.skeleton.presentation.SkeletonSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class OnlySkeletonWSComponent implements WebSocketConfigurer {
    private final SkeletonSocketHandler wsHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler, "/picto")
                .setAllowedOrigins("*");
    }
}
