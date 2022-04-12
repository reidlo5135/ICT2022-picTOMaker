package kr.co.picTO.service.security;

import com.google.gson.Gson;
import kr.co.picTO.advice.exception.CCommunicationException;
import kr.co.picTO.config.security.OAuthRequestFactory;
import kr.co.picTO.dto.social.*;
import kr.co.picTO.entity.oauth.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final OAuthRequestFactory oAuthRequestFactory;
    private final RestTemplate restTemplate;
    private final Gson gson;

    public AccessToken getAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), AccessToken.class);
            }
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    public ProfileDTO getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, provider);
            }
        } catch (Exception e) {
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }

    private ProfileDTO extractProfile(ResponseEntity<String> response, String provider) {
        if (provider.equals("kakao")) {
            KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
            return new ProfileDTO(kakaoProfile.getKakao_account().getEmail());
        } else if(provider.equals("google")) {
            GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
            return new ProfileDTO(googleProfile.getEmail());
        } else {
            NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
            return new ProfileDTO(naverProfile.getResponse().getEmail());
        }
    }
}
