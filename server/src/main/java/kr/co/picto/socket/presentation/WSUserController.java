package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.dto.TokenResponseDto;
import kr.co.picto.user.application.local.UserService;
import kr.co.picto.user.dto.local.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class WSUserController {
    private final UserService userService;

    @MessageMapping(value = "/login")
    @SendTo(value = "/sub/login")
    public ResponseEntity<SingleResult<TokenResponseDto>> loginAndCreateToken(UserLoginDto userLoginDto) {
        log.info("WSUserController login : " + userLoginDto.getEmail());
        return ResponseEntity.ok().body(userService.login(userLoginDto));
    }
}
