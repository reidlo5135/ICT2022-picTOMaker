package kr.co.picto.user.application.social;

import kr.co.picto.user.dto.social.OAuth2RequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Log4j2
@Component
@RequiredArgsConstructor
public class SocialUserFactory {

    private final KakaoInfo kakaoInfo;
    private final GoogleInfo googleInfo;
    private final NaverInfo naverInfo;

    void logRequest(LinkedMultiValueMap<String, String> map) {
        log.info("OAuthFactory Fac Prov Map : " + map);
    }

    public OAuth2RequestDto getRequest(String code, String provider) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        log.info("OAuthFactory prov : " + provider);
        map.add("code", code);

        if (provider.equals("kakao")) {
            map.add("grant_type", "authorization_code");
            map.add("client_id", kakaoInfo.getKakaoClientId());
            map.add("redirect_uri", kakaoInfo.getKakaoRedirect());

            logRequest(map);

            return new OAuth2RequestDto(kakaoInfo.getKakaoTokenUrl(), map);

        } else if(provider.equals("google")) {

            map.add("grant_type", "authorization_code");
            map.add("client_id", googleInfo.getGoogleClientId());
            map.add("client_secret", googleInfo.getGoogleClientSecret());
            map.add("redirect_uri", googleInfo.getGoogleRedirect());

            logRequest(map);

            return new OAuth2RequestDto(googleInfo.getGoogleTokenUrl(), map);
        } else {
            map.add("grant_type", "authorization_code");
            map.add("client_id", naverInfo.getNaverClientId());
            map.add("client_secret", naverInfo.getNaverClientSecret());
            map.add("redirect_uri", naverInfo.getNaverRedirect());
            map.add("state", "project");

            logRequest(map);

            return new OAuth2RequestDto(naverInfo.getNaverTokenUrl(), map);
        }
    }

    public String getProfileUrl(String provider) {
        if (provider.equals("kakao")) {
            return kakaoInfo.getKakaoProfileUrl();
        } else if(provider.equals("google")) {
            return googleInfo.getGoogleProfileUrl();
        } else {
            return naverInfo.getNaverProfileUrl();
        }
    }

    public String getUnlinkUrl(String provider) {
        if(provider.equals("kakao")) {
            return kakaoInfo.getKakaoUnlinkUrl();
        } else {
            return null;
        }
    }

    @Getter
    @Component
    static class KakaoInfo {
        @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
        String kakaoClientId;
        @Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
        String kakaoRedirect;
        @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
        private String kakaoTokenUrl;
        @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
        private String kakaoProfileUrl;
        @Value("${spring.security.oauth2.client.provider.kakao.user-unlink-uri}")
        private String kakaoUnlinkUrl;
    }

    @Getter
    @Component
    static class GoogleInfo {
        @Value("${spring.security.oauth2.client.registration.google.client-id}")
        String googleClientId;
        @Value("${spring.security.oauth2.client.registration.google.redirectUri}")
        String googleRedirect;
        @Value("${spring.security.oauth2.client.registration.google.client-secret}")
        String googleClientSecret;
        @Value("${spring.security.oauth2.client.provider.google.token-uri}")
        private String googleTokenUrl;
        @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
        private String googleProfileUrl;
    }

    @Getter
    @Component
    static class NaverInfo {
        @Value("${spring.security.oauth2.client.registration.naver.client-id}")
        String naverClientId;
        @Value("${spring.security.oauth2.client.registration.naver.redirectUri}")
        String naverRedirect;
        @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
        String naverClientSecret;
        @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
        private String naverTokenUrl;
        @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
        private String naverProfileUrl;
    }
}
