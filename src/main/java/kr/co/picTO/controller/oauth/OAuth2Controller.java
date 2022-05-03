package kr.co.picTO.controller.oauth;

import io.swagger.annotations.Api;
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

    @GetMapping(value = "/redirect/{provider}")
    public void signCallback(@RequestParam("code") String code, @PathVariable String provider, HttpServletResponse response, HttpSession session) {
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


            SingleResult result = responseService.getSingleResult(profileDTO);
            log.info("Prov Controller result GET DATA : " + result.getData());
            session.setAttribute("user", result.getData());
            response.sendRedirect("http://localhost:8080/oauth2/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/login")
    public ResponseEntity<ProfileDTO> login(HttpServletResponse response, HttpSession session) {
        try {
            log.info("Prov Controller login : " + session.getAttribute("user"));

            ProfileDTO profileDTO = (ProfileDTO) session.getAttribute("user");
            URI uri = new URI("http://localhost:8080/");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setLocation(uri);

            log.info("Prov Controller login pDTO : " + profileDTO);
            log.info("Prov Controller login headers : " + httpHeaders);
            return new ResponseEntity<>(profileDTO, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
