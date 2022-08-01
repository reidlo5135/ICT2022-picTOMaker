package kr.co.picTO.controller.oauth;

import io.swagger.annotations.Api;
import kr.co.picTO.service.response.ResponseLoggingService;
import kr.co.picTO.service.security.OAuth2ProviderService;
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

    private static final String className = OAuth2Controller.class.toString();

    private final OAuth2ProviderService OAuth2ProviderService;
    private final ResponseLoggingService loggingService;

    @PostMapping(value = "/token/{provider}")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "generateToken", code.get("code"), provider, "");

        try {
            ett = OAuth2ProviderService.generateAccessToken(code.get("code"), provider);
            log.info("OAuth2Controller gAT ett : " + ett);
            log.info("OAuth2Controller gAT provider : " + provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/profile/{provider}")
    public ResponseEntity<?> getProfile(@RequestBody Map<String, String> access_token, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "getProfile", access_token.get("access_token"), provider, "");

        try {
            ett = OAuth2ProviderService.getProfile(access_token.get("access_token"), provider);
            log.info("Prov Controller ett : " + ett);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @DeleteMapping(value = "/token/invalid/{access_token}")
    public ResponseEntity<?> inValidToken(@PathVariable String access_token) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "inValidToken", access_token, "", "");

        try {
            ett = OAuth2ProviderService.deleteToken(access_token);
            log.info("Prov Controller ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }
}
