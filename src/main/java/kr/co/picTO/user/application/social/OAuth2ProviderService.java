package kr.co.picTO.user.application.social;

import com.google.gson.Gson;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.common.exception.CustomCommunicationException;
import kr.co.picTO.token.domain.BaseAccessToken;
import kr.co.picTO.token.domain.BaseTokenRepo;
import kr.co.picTO.token.dto.SocialTokenRequestDto;
import kr.co.picTO.user.domain.social.AccountRole;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.user.domain.social.SocialUserRepository;
import kr.co.picTO.user.dto.social.*;
import kr.co.picTO.user.exception.CustomAccessTokenExistException;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuth2ProviderService {
    private final OAuth2Factory oAuth2Factory;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final SocialUserRepository userRepo;
    private final BaseTokenRepo tokenRepo;
    private final ResponseService responseService;

    @Transactional
    public SingleResult<SocialTokenRequestDto> generateAccessToken(String code, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuth2RequestDto oAuth2RequestDto = oAuth2Factory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuth2RequestDto.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuth2RequestDto.getUrl(), request, String.class);
        SocialTokenRequestDto tokenRequestDto = gson.fromJson(response.getBody(), SocialTokenRequestDto.class);
        saveAccessToken(tokenRequestDto.toEntity(provider));

        return responseService.getSingleResult(tokenRequestDto);
    }

    @Transactional
    public void saveAccessToken(BaseAccessToken baseAccessToken) {
        log.info("OAuth2ProvSVC sAT bat : " + baseAccessToken);
        if(tokenRepo.findByAccessToken(baseAccessToken.getAccess_token()).isPresent()) throw new CustomAccessTokenExistException();
        tokenRepo.save(baseAccessToken);
    }

    @Transactional
    public SingleResult<Integer> deleteToken(String access_token) {
        log.info("OAuth2ProvSVC deleteToken at : " + access_token);

        return responseService.getSingleResult(tokenRepo.deleteByAccessToken(access_token));
    }

    @Transactional
    public SingleResult<SocialUserProfileResponseDto> getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuth2Factory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        return responseService.getSingleResult(extractProfile(response, provider));
    }

    public SocialUserProfileResponseDto getProfileForGoogle(String accessToken, String provider) {
        String profileUrl = oAuth2Factory.getProfileUrl(provider);

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
    protected SocialUserProfileResponseDto extractProfile(ResponseEntity<String> response, String provider) {
        switch (provider) {
            case "kakao":
                KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + kakaoProfile);

                saveUser(kakaoProfile.getProperties().getNickname(), kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getProfile_image(), provider);
                return SocialUserProfileResponseDto.builder()
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .name(kakaoProfile.getProperties().getNickname())
                        .profile_image_url(kakaoProfile.getProperties().getProfile_image())
                        .build();
            case "google":
                GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + googleProfile);

                saveUser(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getPicture(), provider);
                return SocialUserProfileResponseDto.builder()
                        .email(googleProfile.getEmail())
                        .name(googleProfile.getName())
                        .profile_image_url(googleProfile.getPicture())
                        .build();
            case "naver":
                NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + naverProfile);

                saveUser(naverProfile.getResponse().getName(), naverProfile.getResponse().getEmail(), naverProfile.getResponse().getProfile_image(), provider);
                return SocialUserProfileResponseDto.builder()
                        .email(naverProfile.getResponse().getName())
                        .name(naverProfile.getResponse().getName())
                        .profile_image_url(naverProfile.getResponse().getProfile_image())
                        .build();
            default:
                return null;
        }
    }

    @Transactional
    protected SocialUser saveUser(String name, String email, String picture, String provider){
        if(userRepo.findByEmail(email).isEmpty()) {
            String role = provider.toUpperCase(Locale.ROOT);
            SocialUser socialUser = SocialUser.builder()
                    .name(name)
                    .email(email)
                    .picture(picture)
                    .provider(provider)
                    .role(AccountRole.valueOf(role))
                    .build();
            return userRepo.save(socialUser);
        } else {
            return userRepo.findByEmail(email).orElseThrow(CustomUserNotFoundException::new);
        }
    }
}
