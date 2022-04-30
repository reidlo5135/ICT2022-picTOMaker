package kr.co.picTO.controller.oauth;

import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.oauth2.AccessToken;
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

import javax.servlet.http.HttpSession;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class SocialController {

    private final OAuth2ProviderService OAuth2ProviderService;
    private final ResponseService responseService;

    @GetMapping(value = "/redirect/{provider}")
    public SingleResult<ResponseEntity<ProfileDTO>> signCallback(@RequestParam("code") String code, @PathVariable String provider, HttpSession session) {
        try {
            AccessToken accessToken = OAuth2ProviderService.getAndSaveAccessToken(code, provider);
            log.info("Prov Controller ACCESS TOKEN : " + accessToken);
            log.info("Prov Controller prov : " + provider);

            ProfileDTO profileDTO;

            if(provider.equals("google")) {
                profileDTO = OAuth2ProviderService.getProfileForGoogle(accessToken.getAccess_token(), provider);
                log.info("Prov Controller google pDTO : " + profileDTO);
            } else {
                profileDTO = OAuth2ProviderService.getProfile(accessToken.getAccess_token(), provider);
                log.info("Prov Controller pDTO : " + profileDTO);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            SingleResult result = responseService.getSingleResult(new ResponseEntity<>(profileDTO, headers, HttpStatus.OK));
            log.info("Prov Controller result GET DATA : " + result.getData());
            session.setAttribute("user", result.getData());

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
