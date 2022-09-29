package kr.co.picto.socket.infra;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Log4j2
@Component
@ServerEndpoint(value = "/socket/picto")
public class OnlySkeletonWSHandler {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    private static Map<String, String> data = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        if(!clients.contains(session)) {
            clients.add(session);
            log.info("WebSocket sessionId : " + session.getId());
            log.info("WebSocket sessionRemote : " + session.getBasicRemote());
        } else{
            log.info("WebSocket Session is Already Connected");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("WebSocket onMessage session : " + session.getId());
        log.info("WebSocket send message : " + message);
        for (Session s : clients) {
            if(message.equals("editTool")) {
                s.getBasicRemote().sendText(data.get("skeleton"));
            } else {
                data.put("skeleton", message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        data.clear();
    }
}
