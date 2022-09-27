package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.dto.SocialTokenResponseDto;
import kr.co.picto.user.application.social.SocialUserService;
import kr.co.picto.user.dto.social.SocialUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/user/social/")
public class WSSocialUserController {
    private final SocialUserService socialUserService;

    /**
     * frontend - SocialUserCallback.js
     **/
    @SendTo(value = "/sub/social/login")
    @MessageMapping(value = "/login/{provider}")
    public ResponseEntity<SingleResult<SocialTokenResponseDto>> generateToken(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(socialUserService.generateAccessToken(token, provider));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/social/info")
    @MessageMapping(value = "/info/{provider}")
    public ResponseEntity<SingleResult<SocialUserInfoDto>> getProfile(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(socialUserService.getProfile(token, provider));
    }

    /**
     * frontend - MyPage.js, Sidebar.js
     */
    @SendTo(value = "/sub/social/logout")
    @MessageMapping(value = "/logout")
    public ResponseEntity logout(@Header(value = "X-AUTH-TOKEN") String token) {
        socialUserService.logoutAndDeleteToken(token);
        return ResponseEntity.ok().build();
    }

    /**
     * frontend - MyPage.js
     **/
    @SendTo(value = "/sub/social/drop")
    @MessageMapping(value = "/drop/{provider}")
    public ResponseEntity deActivate(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        socialUserService.delete(token, provider);
        return ResponseEntity.ok().build();
    }
}
