package kr.co.picto.user.application.social;

import com.google.gson.Gson;
import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.common.exception.CustomCommunicationException;
import kr.co.picto.token.dto.SocialTokenResponseDto;
import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.dto.social.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
        SocialUserInfoDto socialUserInfoDto = null;
        switch (provider) {
            case "kakao":
                KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + kakaoProfile);

                socialUserInfoDto = new SocialUserInfoDto(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname(), kakaoProfile.getProperties().getProfile_image(), provider);
                saveUser(socialUserInfoDto);
                return socialUserInfoDto;
            case "google":
                GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + googleProfile);

                socialUserInfoDto = new SocialUserInfoDto(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getPicture(), provider);
                saveUser(socialUserInfoDto);
                return socialUserInfoDto;
            case "naver":
                NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + naverProfile);

                socialUserInfoDto = new SocialUserInfoDto(naverProfile.getResponse().getEmail(), naverProfile.getResponse().getName(), naverProfile.getResponse().getProfile_image(), provider);
                saveUser(socialUserInfoDto);
                return socialUserInfoDto;
            default:
                return socialUserInfoDto;
        }
    }

    @Transactional
    protected void saveUser(SocialUserInfoDto socialUserInfoDto){
        if(socialUserRepository.findByEmail(socialUserInfoDto.getEmail()).isEmpty()) {
            socialUserRepository.save(socialUserInfoDto.from());
        }
    }
}
