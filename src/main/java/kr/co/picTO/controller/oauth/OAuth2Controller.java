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

@Api(tags = {"2. OAuth2 User"})
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class OAuth2Controller {

    private final OAuth2ProviderService OAuth2ProviderService;
    private final ResponseService responseService;
    private final RestTemplate restTemplate;

    @GetMapping(value = "/redirect/{provider}")
    public ResponseEntity<SingleResult<ProfileDTO>> signCallback(@RequestParam("code") String code, @PathVariable String provider, HttpServletResponse response, HttpSession session) {
        try {
            BaseAccessToken baseAccessToken = OAuth2ProviderService.getAndSaveAccessToken(code, provider);
            log.info("Prov Controller ACCESS TOKEN : " + baseAccessToken);
            log.info("Prov Controller prov : " + provider);

            ProfileDTO profileDTO;

            if(provider.equals("google")) {
                profileDTO = OAuth2ProviderService.getProfileForGoogle(baseAccessToken.getAccess_token(), provider);
                log.info("Prov Controller google pDTO : " + profileDTO);
            } else {
                profileDTO = OAuth2ProviderService.getProfile(baseAccessToken.getAccess_token(), provider);
                log.info("Prov Controller pDTO : " + profileDTO);
            }

            SingleResult result = responseService.getSingleResult(profileDTO);
            log.info("Prov Controller result GET DATA : " + result.getData());

            URI uri = new URI("http://localhost:8080/");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<SingleResult<ProfileDTO>> entity = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Prov Controller entity : " + entity);

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
