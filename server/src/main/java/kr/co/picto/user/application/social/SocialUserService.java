package kr.co.picto.user.application.social;

import com.google.gson.Gson;
import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.domain.social.SocialRefreshToken;
import kr.co.picto.token.domain.social.SocialRefreshTokenRepository;
import kr.co.picto.token.dto.SocialTokenResponseDto;
import kr.co.picto.user.domain.AccountStatus;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.dto.social.KakaoProfile;
import kr.co.picto.user.dto.social.NaverProfile;
import kr.co.picto.user.dto.social.OAuth2RequestDto;
import kr.co.picto.user.dto.social.SocialUserInfoDto;
import kr.co.picto.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final Gson gson;
    private final RestTemplate restTemplate;
    private final ResponseService responseService;
    private final SocialUserFactory socialUserFactory;
    private final SocialUserRepository socialUserRepository;
    private final SocialRefreshTokenRepository socialRefreshTokenRepository;

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
    public void renewLogin(Long userId, String accessToken) {
        socialRefreshTokenRepository.updateById(accessToken, userId);
    }

    @Transactional
    public SingleResult<SocialUserInfoDto> getProfile(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = socialUserFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        return responseService.getSingleResult(extractProfile(response, accessToken, provider));
    }

    @Transactional
    protected SocialUserInfoDto extractProfile(ResponseEntity<String> response, String accessToken, String provider) {
        SocialUserInfoDto socialUserInfoDto = null;
        switch (provider) {
            case "kakao":
                KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + kakaoProfile);

                socialUserInfoDto = new SocialUserInfoDto(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname(), kakaoProfile.getProperties().getProfile_image(), provider);
                saveUser(socialUserInfoDto, accessToken);
                return socialUserInfoDto;
            case "naver":
                NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
                log.info("OAuth2ProvSVC " + provider + " extractProfile : " + naverProfile);

                socialUserInfoDto = new SocialUserInfoDto(naverProfile.getResponse().getEmail(), naverProfile.getResponse().getName(), naverProfile.getResponse().getProfile_image(), provider);
                saveUser(socialUserInfoDto, accessToken);
                return socialUserInfoDto;
            default:
                return socialUserInfoDto;
        }
    }

    @Transactional
    public void logoutAndDeleteToken(String access_token) {
        SocialUser socialUser = socialUserRepository.findById(findIdByToken(access_token)).orElseThrow(CustomUserNotFoundException::new);
        socialRefreshTokenRepository.deleteByTokenId(socialUser.getId());
    }

    @Transactional
    protected void saveUser(SocialUserInfoDto socialUserInfoDto, String accessToken){
        if(socialUserRepository.findByEmailAndProvider(socialUserInfoDto.getEmail(), socialUserInfoDto.getProvider()).isEmpty()) {
            socialUserRepository.save(socialUserInfoDto.from());
        }
        SocialUser socialUser = socialUserRepository.findByEmailAndProvider(socialUserInfoDto.getEmail(), socialUserInfoDto.getProvider()).orElseThrow(CustomUserNotFoundException::new);
        if(socialRefreshTokenRepository.findByTokenId(socialUser.getId()).isPresent()) {
            if(isInActiveUser(socialUser)) socialUser.activate();
            renewLogin(socialUser.getId(), accessToken);
        } else {
            SocialRefreshToken socialRefreshToken = SocialRefreshToken.builder()
                    .tokenId(socialUser.getId())
                    .token(accessToken)
                    .build();
            socialRefreshTokenRepository.save(socialRefreshToken);
        }
    }

    @Transactional
    public Long findIdByToken(String accessToken) {
        log.info("findIdByToken : " + accessToken);
        Long userId = socialRefreshTokenRepository.findIdByToken(accessToken);
        log.info("findIdByToken userId : " + userId);
        return userId;
    }

    @Transactional
    public void delete(String accessToken, String provider) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String unlinkUrl = socialUserFactory.getUnlinkUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(unlinkUrl, request, String.class);
        log.info("SocialUser SVC delete response : " + response);

        SocialUser socialUser = socialUserRepository.findById(findIdByToken(accessToken)).orElseThrow(CustomUserNotFoundException::new);
        socialUser.deactivate();
    }

    public boolean isInActiveUser(SocialUser socialUser) {
        return socialUser.getStatus() == AccountStatus.INACTIVE;
    }
}
