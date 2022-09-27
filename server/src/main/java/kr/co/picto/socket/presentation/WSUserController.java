package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.dto.TokenRequestDto;
import kr.co.picto.token.dto.TokenResponseDto;
import kr.co.picto.user.application.local.UserService;
import kr.co.picto.user.dto.local.UserCreateDto;
import kr.co.picto.user.dto.local.UserInfoDto;
import kr.co.picto.user.dto.local.UserLoginDto;
import kr.co.picto.user.dto.local.UserUpdateDto;
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
public class WSUserController {
    private final UserService userService;

    /**
     * frontend - Mobile_main.js
     **/
    @SendTo(value = "/sub/login")
    @MessageMapping(value = "/login")
    public ResponseEntity<SingleResult<TokenResponseDto>> loginAndCreateToken(UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.login(userLoginDto));
    }

    /**
     * frontend - LocalUserSignUp.js
     **/
    @SendTo(value = "/sub/signup")
    @MessageMapping(value = "/signup")
    public ResponseEntity<SingleResult<Long>> save(UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.signUp(userCreateDto));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/info")
    @MessageMapping(value = "/info")
    public ResponseEntity<SingleResult<UserInfoDto>> getProfile(@Header(value = "X-AUTH-TOKEN") String token) {
        log.info("INFO TOKEN : " + token);
        return ResponseEntity.ok().body(userService.info(token));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/reissue")
    @MessageMapping(value = "/reissue")
    public ResponseEntity<SingleResult<TokenResponseDto>> reissue(TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.reissue(tokenRequestDto));
    }

    /**
     * frontend - App.js
     */
    @SendTo(value = "/sub/user/up")
    @MessageMapping(value = "/up")
    public ResponseEntity<SingleResult<UserInfoDto>> update(UserUpdateDto userUpdateDto, @Header(value = "X-AUTH-TOKEN") String token) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, token));
    }

    /**
     * frontend - MyPage.js
     **/
    @SendTo(value = "/sub/user/drop")
    @MessageMapping(value = "/drop")
    public ResponseEntity deActivate(@Header(value = "X-AUTH-TOKEN") String token) {
        userService.delete(token);
        return ResponseEntity.ok().build();
    }

    /**
     * frontend - MyPage.js, Sidebar.js
     */
    @SendTo(value = "/sub/user/logout")
    @MessageMapping(value = "/logout")
    public ResponseEntity logout(@Header(value = "X-AUTH-TOKEN") String token) {
        userService.logoutAndDeleteToken(token);
        return ResponseEntity.ok().build();
    }
}
