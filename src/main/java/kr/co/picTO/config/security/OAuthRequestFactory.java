package kr.co.picTO.config.security;

import kr.co.picTO.dto.social.OAuthRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URI;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuthRequestFactory {

    private final KakaoInfo kakaoInfo;
    private final GoogleInfo googleInfo;
    private final NaverInfo naverInfo;

    public OAuthRequest getRequest(String code, String provider) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        log.info("OAuthFactory prov : " + provider);
        if (provider.equals("kakao")) {
            map.add("grant_type", "authorization_code");
            map.add("client_id", kakaoInfo.getKakaoClientId());
            map.add("redirect_uri", kakaoInfo.getKakaoRedirect());
            map.add("code", code);

            return new OAuthRequest(kakaoInfo.getKakaoTokenUrl(), map);

        } else if(provider.equals("google")) {

            map.add("grant_type", "authorization_code");
            map.add("client_id", googleInfo.getGoogleClientId());
            map.add("client_secret", googleInfo.getGoogleClientSecret());
            map.add("redirect_uri", googleInfo.getGoogleRedirect());
            map.add("code", code);

            return new OAuthRequest(googleInfo.getGoogleTokenUrl(), map);
        } else {
            map.add("grant_type", "authorization_code");
            map.add("client_id", naverInfo.getNaverClientId());
            map.add("client_secret", naverInfo.getNaverClientSecret());
            map.add("redirect_uri", naverInfo.getNaverRedirect());
            map.add("state", "project");
            map.add("code", code);

            return new OAuthRequest(naverInfo.getNaverTokenUrl(), map);
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
    }

    @Getter
    @Component
    static class GoogleInfo {
        @Value("${spring.security.oauth2.client.registration.google.client-id}")
        String googleClientId;

        String googleRedirect = "http://localhost:8080/picTOmaker.com/account/signcallback/google";

        @Value("${spring.security.oauth2.client.registration.google.client-secret}")
        String googleClientSecret;

        private String googleTokenUrl = "https://www.googleapis.com/oauth2/v4/token";

        private String googleProfileUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
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
