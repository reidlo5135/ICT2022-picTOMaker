package kr.co.picto.user.presentation;

import io.swagger.annotations.Api;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.dto.SocialTokenRequestDto;
import kr.co.picto.user.application.social.SocialUserService;
import kr.co.picto.user.dto.social.SocialUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"2. OAuth2 User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth2")
public class SocialUserController {
    private final SocialUserService socialUserService;

    /**
     * frontend - SocialUserCallback.js
     **/
    @PostMapping(value = "/signup/{provider}")
    public ResponseEntity<SingleResult<SocialTokenRequestDto>> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.generateAccessToken(code.get("code"), provider));
    }

    /**
     * frontend - TopProfile.js
     **/
    @PostMapping(value = "/info/{provider}")
    public ResponseEntity<SingleResult<SocialUserInfoDto>> getProfile(@RequestBody Map<String, String> access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.getProfile(access_token.get("access_token"), provider));
    }

    /**
     * frontend - MyPage.js, Sidebar.js
     **/
    @DeleteMapping(value = "/{access_token}")
    public ResponseEntity<SingleResult<Integer>> inValidToken(@PathVariable String access_token) {
        return ResponseEntity.ok().body(socialUserService.deleteToken(access_token));
    }
}
