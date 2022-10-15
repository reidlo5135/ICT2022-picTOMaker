package kr.co.picto.user.presentation;

import kr.co.picto.user.application.UserService;
import kr.co.picto.user.dto.UserCreateDto;
import kr.co.picto.user.dto.UserLoginDto;
import kr.co.picto.user.dto.UserTokenRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/user/")
public class UserController {
    private final UserService userService;

    /**
     * frontend - Mobile_main.js
     **/
    @SendTo(value = "/sub/login")
    @MessageMapping(value = "/login")
    public ResponseEntity loginAndCreateToken(UserLoginDto userLoginDto) {
        log.info("UserController login email : " + userLoginDto.getEmail());
        return ResponseEntity.ok().body(userService.requestLogin(userLoginDto));
    }

    /**
     * frontend - LocalUserSignUp.js
     **/
    @SendTo(value = "/sub/signup")
    @MessageMapping(value = "/signup")
    public ResponseEntity save(UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.requestSignUp(userCreateDto));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/info")
    @MessageMapping(value = "/info")
    public ResponseEntity getProfile(@Header(value = "X-AUTH-TOKEN") String token) {
        log.info("INFO TOKEN : " + token);
        return ResponseEntity.ok().body(userService.requestInfo(token));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/reissue")
    @MessageMapping(value = "/reissue")
    public ResponseEntity reissue(UserTokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.requestReissue(tokenRequestDto));
    }

    /**
     * frontend - MyPage.js
     **/
    @SendTo(value = "/sub/user/drop")
    @MessageMapping(value = "/drop")
    public ResponseEntity deActivate(@Header(value = "X-AUTH-TOKEN") String token) {
        userService.requestDelete(token);
        return ResponseEntity.ok().build();
    }

    /**
     * frontend - MyPage.js, Sidebar.js
     */
    @SendTo(value = "/sub/user/logout")
    @MessageMapping(value = "/logout")
    public ResponseEntity logout(@Header(value = "X-AUTH-TOKEN") String token) {
        userService.requestLogout(token);
        return ResponseEntity.ok().build();
    }
}
