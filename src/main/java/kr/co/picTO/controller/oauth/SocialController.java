package kr.co.picTO.controller.oauth;

import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.oauth.AccessToken;
import kr.co.picTO.service.security.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final ProviderService providerService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    private String kakaoRedirect;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.provider.naver.authorization_uri}")
    private String naverAuthorUri;

    @Value("${spring.security.oauth2.client.registration.naver.redirectUri}")
    private String naverRedirect;

    @GetMapping(value = "/social/login")
    public ModelAndView socialKakaoLogin(ModelAndView mav) {
        StringBuilder kakaoUrl = new StringBuilder()
                .append("http://localhost:8080/picTOmaker.com/oauth2/authorization/kakao")
                .append("?client-id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(kakaoRedirect);

//        StringBuilder googleUrl = new StringBuilder()
//                .append("https://accounts.google.com/o/oauth2/auth?")
//                .append("scope=https://www.googleapis.com/auth/userinfo.email&")
//                .append("access_type=offline&")
//                .append("include_granted_scopes=true&")
//                .append("response_type=code&")
//                .append("state=state_parameter_passthrough_value&")
//                .append("redirect_uri=").append("http://localhost:8080/picTOmaker.com/account/signcallback/google&")
//                .append("client_id=").append(googleClientId);
        StringBuilder googleUrl = new StringBuilder()
                .append("https://accounts.google.com/o/oauth2/auth?")
                .append("client_id=").append(googleClientId)
                .append("&redirect_uri=").append("http://localhost:8080/picTOmaker.com/account/signcallback/google")
                .append("&response_type=code")
                .append("&scope=https://www.googleapis.com/auth/userinfo.profile")
                .append("&approval_prompt=force")
                .append("&access_type=offline");

        StringBuilder naverUrl = new StringBuilder()
                .append(naverAuthorUri)
                .append("?client_id=").append(naverClientId)
                .append("&response_type=code")
                .append("&state=project")
                .append("&redirect_uri=").append(naverRedirect);

        mav.addObject("loginUrl1", kakaoUrl);
        mav.addObject("loginUrl2", googleUrl);
        mav.addObject("loginUrl3", naverUrl);
        mav.setViewName("social/login");
        return mav;
    }

    @GetMapping(value = "/account/signcallback/{provider}")
    public ModelAndView GoogleSignCallback(ModelAndView mav, @RequestParam("code") String code, @PathVariable String provider) {
        try {
            AccessToken accessToken = providerService.getAccessToken(code, provider);
            log.info("Prov Controller ACCESS TOKEN : " + accessToken);
            log.info("Prov Controller prov : " + provider);

            ProfileDTO profileDTO = providerService.getProfile(accessToken.getAccess_token(), provider);
            log.info("Prov Controller pDTO : " + profileDTO);

            mav.addObject("Provider", provider);
            mav.addObject("code", accessToken);
            mav.addObject("profile", profileDTO);
            mav.setViewName("social/redirect");

            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            mav.addObject("code", "TOKEN IS NULL");
            mav.setViewName("social/redirect");
            return mav;
        }
    }
}
