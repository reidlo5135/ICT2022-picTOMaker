package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.qna.application.UserQnaService;
import kr.co.picto.qna.dto.UserQnaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/qna/")
public class WSQnaController {
    private final UserQnaService userQnaService;

    /**
     * frontend - QnA.js
     **/
    @SendTo(value = "/sub/qna/register")
    @MessageMapping(value = "/register/{provider}")
    public ResponseEntity<SingleResult<Long>> save(UserQnaRequestDto userQnaRequestDto, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(userQnaService.registerQnA(userQnaRequestDto, provider));
    }
}
