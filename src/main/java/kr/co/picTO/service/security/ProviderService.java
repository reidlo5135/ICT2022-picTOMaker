package kr.co.picTO.service.security;

import com.google.gson.Gson;
import kr.co.picTO.advice.exception.CCommunicationException;
import kr.co.picTO.config.security.OAuthRequestFactory;
import kr.co.picTO.dto.social.*;
import kr.co.picTO.entity.oauth.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
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

        log.info("Prov SVC prov : " + provider);
        log.info("Prov SVC getMap : " + oAuthRequest.getMap());
        log.info("Prov SVC getUrl : " + oAuthRequest.getUrl());

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Prov SVC gson GetBody : " + gson.fromJson(response.getBody(), AccessToken.class));
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
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        log.info("Prov SVC profileUrl : " + profileUrl);
        log.info("Prov SVC req : " + request);
        log.info("Prov SVC res : " + response);

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
            log.info("Prov SVC extractProfile : " + kakaoProfile);
            return new ProfileDTO(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname(), kakaoProfile.getProperties().getProfile_image());
        } else if(provider.equals("google")) {
            GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
            return new ProfileDTO(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getImgUrl());
        } else {
            NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
            return new ProfileDTO(naverProfile.getResponse().getEmail(), naverProfile.getResponse().getName(), naverProfile.getResponse().getImgUrl());
        }
    }
}
