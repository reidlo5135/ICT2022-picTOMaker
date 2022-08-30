package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.dto.SocialTokenRequestDto;
import kr.co.picTO.user.application.social.OAuth2ProviderService;
import kr.co.picTO.user.dto.social.SocialUserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"2. OAuth2 User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth2")
public class OAuth2Controller {
    private final OAuth2ProviderService oAuth2ProviderService;

    @PostMapping(value = "/register/provider/{provider}")
    public ResponseEntity<SingleResult<SocialTokenRequestDto>> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        return ResponseEntity.ok().body(oAuth2ProviderService.generateAccessToken(code.get("code"), provider));
    }

    @PostMapping(value = "/profile/provider/{provider}")
    public ResponseEntity<SingleResult<SocialUserProfileResponseDto>> getProfile(@RequestBody Map<String, String> access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(oAuth2ProviderService.getProfile(access_token.get("access_token"), provider));
    }

    @DeleteMapping(value = "/token/{access_token}")
    public ResponseEntity<SingleResult<Integer>> inValidToken(@PathVariable String access_token) {
        return ResponseEntity.ok().body(oAuth2ProviderService.deleteToken(access_token));
    }
}
