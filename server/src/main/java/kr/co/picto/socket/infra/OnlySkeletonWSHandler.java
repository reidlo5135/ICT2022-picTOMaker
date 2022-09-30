package kr.co.picto.socket.infra;

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
public class OnlySkeletonWSHandler extends TextWebSocketHandler {
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    private static Map<String, JSONObject> data = new HashMap<>();

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
            WebSocketSession wss = sessionMap.get(key);
            try {
                if(msg.equals("editTool")) {
                    log.info("EditTool DataMap : " + data);
                    wss.sendMessage(new TextMessage(data.get("toBrowser").toJSONString()));
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
