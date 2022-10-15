package kr.co.picto.common.infra;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class WebSocketHandler implements ChannelInterceptor {
//    private final JwtProvider jwtProvider;

//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
//        log.info("WSHandler msg : " + message);
//        log.info("WSHandler channel : " + channel);
//        log.info("WSHandler accessor : " + stompHeaderAccessor);
//        if(StompCommand.CONNECT == stompHeaderAccessor.getCommand()) {
//            jwtProvider.validationToken(stompHeaderAccessor.getFirstNativeHeader("X-AUTH-TOKEN"));
//        }
//        return message;
//    }
}
