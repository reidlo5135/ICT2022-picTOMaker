package kr.co.picto.common.infra;

import kr.co.picto.skeleton.application.SkeletonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class OnlySkeletonWSHandler extends TextWebSocketHandler {
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    private static Map<String, JSONObject> data = new HashMap<>();
    private final SkeletonService skeletonService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionMap.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
        log.info("payload : " + msg);
        JSONObject obj = jsonToObjectParser(msg);
        log.info("JSONObj : " + obj);

        for(String key : sessionMap.keySet()) {
            log.info("SessionMap key : " +  key);
            WebSocketSession wss = sessionMap.get(key);
            log.info("SessionMap id : " + wss.getId());
            log.info("is Equal : " + (key.equals(wss.getId())));
            try {
                if(msg.equals("editTool")) {
                    log.info("EditTool DataMap : " + data);
                    if(data.isEmpty()) {
                        wss.sendMessage(new TextMessage("empty"));
                    } else {
                        wss.sendMessage(new TextMessage(data.get("toBrowser").toJSONString()));
                    }
                } else {
                    data.put("toBrowser", obj);
                }
                wss.sendMessage(new TextMessage(obj.toJSONString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
        data.clear();
        super.afterConnectionClosed(session, status);
    }

    private static JSONObject jsonToObjectParser(String jsonStr) {
        log.info("JSON STR : " + jsonStr);
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
