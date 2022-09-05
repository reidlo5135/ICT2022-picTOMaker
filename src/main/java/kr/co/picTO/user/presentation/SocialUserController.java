package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.dto.SocialTokenRequestDto;
import kr.co.picTO.user.application.social.SocialUserService;
import kr.co.picTO.user.dto.social.SocialUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"2. OAuth2 User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth2")
public class SocialUserController {
    private final SocialUserService socialUserService;

    @PostMapping(value = "/register/provider/{provider}")
    public ResponseEntity<SingleResult<SocialTokenRequestDto>> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.generateAccessToken(code.get("code"), provider));
    }

    @PostMapping(value = "/profile/provider/{provider}")
    public ResponseEntity<SingleResult<SocialUserInfoDto>> getProfile(@RequestBody Map<String, String> access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.getProfile(access_token.get("access_token"), provider));
    }

    @DeleteMapping(value = "/token/{access_token}")
    public ResponseEntity<SingleResult<Integer>> inValidToken(@PathVariable String access_token) {
        return ResponseEntity.ok().body(socialUserService.deleteToken(access_token));
    }
}
