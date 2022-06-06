package kr.co.picTO.service.security;

import com.google.gson.Gson;
import kr.co.picTO.advice.exception.CustomCommunicationException;
import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.config.security.OAuthRequestFactory;
import kr.co.picTO.dto.social.*;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.entity.oauth2.BaseAuthRole;
import kr.co.picTO.entity.oauth2.BaseAuthUser;
import kr.co.picTO.repository.BaseAuthUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
@Log4j2
@RequiredArgsConstructor
public class OAuth2ProviderService {

    private final OAuthRequestFactory oAuthRequestFactory;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final BaseAuthUserRepo userRepo;
    private final BaseTokenRepo tokenRepo;

    @Transactional
    public BaseAccessToken generateAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);

        log.info("Prov SVC gAT request : " + request);
        log.info("Prov SVC gAT code : " + code);
        log.info("Prov SVC gAT prov : " + provider);
        log.info("Prov SVC gAT getMap : " + oAuthRequest.getMap());
        log.info("Prov SVC gAT getUrl : " + oAuthRequest.getUrl());

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        log.warn("Prov SVC gAT resEntity : " + response);

        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                BaseAccessToken baseAccessToken = gson.fromJson(response.getBody(), BaseAccessToken.class);
                baseAccessToken.setProvider(provider.toUpperCase(Locale.ROOT));

                log.info("Prov SVC gAT gson GetBody : " + baseAccessToken);
                return baseAccessToken;
            } else if(response.getStatusCode() != HttpStatus.OK) {
                log.error("Prov SVC gAT getBody : " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Prov SVC gAT CCommunicateException Occurred" + e.getMessage());
            throw new CustomCommunicationException();
        }
        throw new CustomCommunicationException();
    }

    public void saveAccessToken(BaseAccessToken baseAccessToken) {
        log.info("Prov SVC sAT bat : " + baseAccessToken);
        try {
            if(!tokenRepo.findByAccessToken(baseAccessToken.getAccess_token()).isPresent()) {
                tokenRepo.save(baseAccessToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Prov SVC sAT Error Occurred : " + e.getMessage());
            throw new CustomCommunicationException();
        }
    }

    @Transactional
    public Integer deleteToken(String access_token) {
        log.info("Prov SVC delT at : " + access_token);
        Integer id = null;
        try {
            id = tokenRepo.deleteByAccessToken(access_token);
            log.info("Prov SVC delT bat id : " + id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Prov SVC delT Error Occurred : " + e.getMessage());
            throw new CustomCommunicationException();
        }
        return id;
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
            log.info("Prov SVC res statusCode : "+ response.getStatusCode());
            log.info("Prov SVC res getBody : " + response.getBody());
            log.info("Prov SVC res getHeaders : " + response.getHeaders());
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, accessToken, provider);
            }
        } catch (Exception e) {
            log.error("CCommunicate exception" + e.getMessage());
            throw new CustomCommunicationException();
        }
        throw new CustomCommunicationException();
    }

    public ProfileDTO getProfileForGoogle(String accessToken, String provider) {
        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);

        ResponseEntity<String> response = null;

        if(provider.equals("google")) {
            String googleProfUrl = profileUrl + "?access_token=" + accessToken;
            log.info("Prov SVC googlePF profileURL : " + googleProfUrl);

            response = restTemplate.getForEntity(googleProfUrl, String.class);

            log.info("Prov SVC googlePF res : " + response);
        }

        try {
            log.info("Prov SVC googlePF res statusCode : "+ response.getStatusCode());
            log.info("Prov SVC googlePF res getBody : " + response.getBody());
            log.info("Prov SVC googlePF res getHeaders : " + response.getHeaders());
            if(response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, accessToken, provider);
            }
        } catch (Exception e){
            log.error("CCoummuicate exception : " + e.getMessage());
            throw new CustomCommunicationException();
        }
        throw new CustomCommunicationException();
    }

    private ProfileDTO extractProfile(ResponseEntity<String> response, String accessToken, String provider) {
        if (provider.equals("kakao")) {
            KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
            log.info("Prov SVC " + provider + " extractProfile : " + kakaoProfile);

            saveUser(accessToken, kakaoProfile.getProperties().getNickname(), kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getProfile_image(), provider);
            return new ProfileDTO(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname(), kakaoProfile.getProperties().getProfile_image());
        } else if(provider.equals("google")) {
            GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
            log.info("Prov SVC " + provider + " extractProfile : " + googleProfile);

            saveUser(accessToken, googleProfile.getEmail(), googleProfile.getName(), googleProfile.getPicture(), provider);
            return new ProfileDTO(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getPicture());
        } else {
            NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
            log.info("Prov SVC " + provider + " extractProfile : " + naverProfile);

            saveUser(accessToken, naverProfile.getResponse().getName(), naverProfile.getResponse().getEmail(), naverProfile.getResponse().getProfile_image(), provider);
            return new ProfileDTO(naverProfile.getResponse().getEmail(), naverProfile.getResponse().getName(), naverProfile.getResponse().getProfile_image());
        }
    }

    private BaseAuthUser saveUser(String accessToken, String name, String email, String picture, String provider){
        if(!userRepo.findByEmail(email).isPresent()) {
            String role = provider.toUpperCase(Locale.ROOT);
            BaseAuthUser baseAuthUser = BaseAuthUser.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .provider(provider)
                    .role(BaseAuthRole.valueOf(role))
                    .build();
            log.info("Prov SVC saveUser : " + baseAuthUser.toString());
            return userRepo.save(baseAuthUser);
        } else {
            return userRepo.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);
        }
    }
}
