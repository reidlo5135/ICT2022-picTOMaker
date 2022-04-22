package kr.co.picTO.controller.oauth;

import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.oauth.AccessToken;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.security.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class SocialController {

    private final ProviderService providerService;
    private final ResponseService responseService;

    @GetMapping(value = "/redirect/{provider}")
    public SingleResult<ProfileDTO> signCallback(@RequestParam("code") String code, @PathVariable String provider) {
        try {
            AccessToken accessToken = providerService.getAndSaveAccessToken(code, provider);
            log.info("Prov Controller ACCESS TOKEN : " + accessToken);
            log.info("Prov Controller prov : " + provider);

            ProfileDTO profileDTO = providerService.getProfile(accessToken.getAccess_token(), provider);
            log.info("Prov Controller pDTO : " + profileDTO);

            return responseService.getSingleResult(profileDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
