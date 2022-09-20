package kr.co.picto.user.application.social;

import com.google.gson.Gson;
import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.common.exception.CustomCommunicationException;
import kr.co.picto.token.dto.SocialTokenResponseDto;
import kr.co.picto.user.domain.AccountRole;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.dto.social.*;
import kr.co.picto.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Log4j2
@Service
@RequiredArgsConstructor
public class SocialUserService {
    private final SocialUserFactory socialUserFactory;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final SocialUserRepository socialUserRepository;
    private final ResponseService responseService;

    @Transactional
    public SingleResult<SocialTokenResponseDto> generateAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuth2RequestDto oAuth2RequestDto = socialUserFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuth2RequestDto.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuth2RequestDto.getUrl(), request, String.class);
        SocialTokenResponseDto socialTokenResponseDto = gson.fromJson(response.getBody(), SocialTokenResponseDto.class);

        return responseService.getSingleResult(socialTokenResponseDto);
    }

    @Transactional
    public SingleResult<SocialUserInfoDto> getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = socialUserFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        return responseService.getSingleResult(extractProfile(response, provider));
    }

    public SocialUserInfoDto getProfileForGoogle(String accessToken, String provider) {
        String profileUrl = socialUserFactory.getProfileUrl(provider);

        ResponseEntity<String> response = null;

        if(provider.equals("google")) {
            response = restTemplate.getForEntity(profileUrl + "?access_token=" + accessToken, String.class);
        }

        if(response.getStatusCode() == HttpStatus.OK) {
            return extractProfile(response, provider);
        } else {
            throw new CustomCommunicationException();
        }
    }

    @Transactional
    protected SocialUserInfoDto extractProfile(ResponseEntity<String> response, String provider) {
        switch (provider) {
            case "kakao":
                KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + kakaoProfile);

                saveUser(kakaoProfile.getProperties().getNickname(), kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getProfile_image(), provider);
                return SocialUserInfoDto.builder()
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .name(kakaoProfile.getProperties().getNickname())
                        .profile_image_url(kakaoProfile.getProperties().getProfile_image())
                        .build();
            case "google":
                GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + googleProfile);

                saveUser(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getPicture(), provider);
                return SocialUserInfoDto.builder()
                        .email(googleProfile.getEmail())
                        .name(googleProfile.getName())
                        .profile_image_url(googleProfile.getPicture())
                        .build();
            case "naver":
                NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + naverProfile);

                saveUser(naverProfile.getResponse().getName(), naverProfile.getResponse().getEmail(), naverProfile.getResponse().getProfile_image(), provider);
                return SocialUserInfoDto.builder()
                        .email(naverProfile.getResponse().getName())
                        .name(naverProfile.getResponse().getName())
                        .profile_image_url(naverProfile.getResponse().getProfile_image())
                        .build();
            default:
                return null;
        }
    }

    @Transactional
    protected void saveUser(String name, String email, String picture, String provider){
        if(socialUserRepository.findByEmail(email).isEmpty()) {
            String role = provider.toUpperCase(Locale.ROOT);
            SocialUser socialUser = SocialUser.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .provider(provider)
                    .role(AccountRole.valueOf(role))
                    .build();
            socialUserRepository.save(socialUser);
        } else {
            socialUserRepository.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);
        }
    }
}
