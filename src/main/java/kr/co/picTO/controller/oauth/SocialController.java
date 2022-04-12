package kr.co.picTO.controller.oauth;

import kr.co.picTO.service.security.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/social/login")
public class SocialController {

    private final Environment env;
    private final ProviderService providerService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
    private String kakaoRedirect;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.redirectUri}")
    private String naverRedirect;

    @GetMapping()
    public ModelAndView socialKakaoLogin(ModelAndView mav) {
        StringBuilder kakaoUrl = new StringBuilder()
                .append("http://localhost:8080/picTOmaker.com/oauth2/authorization/kakao")
                .append("?client-id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(kakaoRedirect);

        StringBuilder googleUrl = new StringBuilder()
                .append("https://accounts.google.com/o/oauth2/auth")
                .append("?client_id=").append(googleClientId)
                .append("&response_type=code")
                .append("&scope=email%20profile")
                .append("&redirect_uri=").append("http://localhost:8080/picTOmaker.com/login/oauth2/code/google");

        StringBuilder naverUrl = new StringBuilder()
                .append("http://localhost:8080/picTOmaker.com/oauth2.0/authorization/naver")
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

    @GetMapping(value = "/{provider}")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code, @PathVariable String provider) {
        mav.addObject("code", code);
        mav.setViewName("social/redirect");
        return mav;
    }
}
