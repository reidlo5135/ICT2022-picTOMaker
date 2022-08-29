package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.user.application.social.OAuth2ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"2. OAuth2 User"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth2")
public class OAuth2Controller {
    private final String ClassName = this.getClass().getName();
    private final OAuth2ProviderService oAuth2ProviderService;
    private final ResponseLoggingService loggingService;

    @PostMapping(value = "/register/provider/{provider}")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        loggingService.httpPathStrLoggingWithRequest(ClassName, "generateToken", code.get("code"), provider, "");
        return ResponseEntity.ok().body(oAuth2ProviderService.generateAccessToken(code.get("code"), provider));
    }

    @PostMapping(value = "/profile/provider/{provider}")
    public ResponseEntity<?> getProfile(@RequestBody Map<String, String> access_token, @PathVariable String provider) {
        loggingService.httpPathStrLoggingWithRequest(ClassName, "getProfile", access_token.get("access_token"), provider, "");
        return ResponseEntity.ok().body(oAuth2ProviderService.getProfile(access_token.get("access_token"), provider));
    }

    @DeleteMapping(value = "/token/{access_token}")
    public ResponseEntity<?> inValidToken(@PathVariable String access_token) {
        loggingService.httpPathStrLogging(ClassName, "inValidToken", access_token, "", "");
        return ResponseEntity.ok().body(oAuth2ProviderService.deleteToken(access_token));
    }
}
