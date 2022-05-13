package kr.co.picTO.controller.oauth;

import io.swagger.annotations.Api;
import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.security.OAuth2ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Map;

@Api(tags = {"2. OAuth2 User"})
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class OAuth2Controller {

    private final OAuth2ProviderService OAuth2ProviderService;
    private final ResponseService responseService;

    @PostMapping(value = "/token/{provider}")
    public ResponseEntity<SingleResult<BaseAccessToken>> generateToken(@RequestBody Map<String, String> code, @PathVariable String provider) {
        ResponseEntity<SingleResult<BaseAccessToken>> ett = null;
        log.info("Prov Controller code : " + code.get("code"));

        try {
            BaseAccessToken baseAccessToken = OAuth2ProviderService.getAndSaveAccessToken(code.get("code"), provider);
            log.info("Prov Controller ACCESS TOKEN : " + baseAccessToken);
            log.info("Prov Controller prov : " + provider);

            SingleResult<BaseAccessToken> result = responseService.getSingleResult(baseAccessToken);
            log.info("Prov Controller token result : " + result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Prov Controller ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/profile/{provider}")
    public ResponseEntity<SingleResult<ProfileDTO>> getProfile(@RequestBody String access_token, @PathVariable String provider) {
        ResponseEntity<SingleResult<ProfileDTO>> ett = null;
        log.info("Prov Controller access_token : " + access_token);
        log.info("Prov Controller prov : " + provider);

        try {
            ProfileDTO profileDTO;

            if(provider.equals("google")) {
                profileDTO = OAuth2ProviderService.getProfileForGoogle(access_token, provider);
                log.info("Prov Controller google pDTO : " + profileDTO);
            } else {
                profileDTO = OAuth2ProviderService.getProfile(access_token, provider);
                log.info("Prov Controller pDTO : " + profileDTO);
            }

            SingleResult result = responseService.getSingleResult(profileDTO);
            log.info("Prov Controller result GET DATA : " + result.getData());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Prov Controller ett : " + ett);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }
}
