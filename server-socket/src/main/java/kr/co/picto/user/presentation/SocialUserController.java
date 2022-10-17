package kr.co.picto.user.presentation;

import kr.co.picto.user.application.SocialUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/user/social/")
public class SocialUserController {
    private final SocialUserService socialUserService;

    /**
     * frontend - SocialUserCallback.js
     **/
    @SendTo(value = "/sub/social/login")
    @MessageMapping(value = "/login/{provider}")
    public ResponseEntity generateToken(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(socialUserService.requestSocialLogin(token, provider));
    }

    /**
     * frontend - TopProfile.js
     **/
    @SendTo(value = "/sub/social/info")
    @MessageMapping(value = "/info/{provider}")
    public ResponseEntity getProfile(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(socialUserService.requestSocialProfile(token, provider));
    }

    /**
     * frontend - MyPage.js, Sidebar.js
     */
    @SendTo(value = "/sub/social/logout")
    @MessageMapping(value = "/logout")
    public ResponseEntity logout(@Header(value = "X-AUTH-TOKEN") String token) {
        socialUserService.requestSocialLogout(token);
        return ResponseEntity.ok().build();
    }

    /**
     * frontend - MyPage.js
     **/
    @SendTo(value = "/sub/social/drop")
    @MessageMapping(value = "/drop/{provider}")
    public ResponseEntity deActivate(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        socialUserService.requestDeActive(token, provider);
        return ResponseEntity.ok().build();
    }
}
