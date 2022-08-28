package kr.co.picTO.member.application.local;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.exception.CustomEmailLoginFailedException;
import kr.co.picTO.member.exception.CustomRefreshTokenException;
import kr.co.picTO.member.exception.CustomUserExistException;
import kr.co.picTO.member.exception.CustomUserNotFoundException;
import kr.co.picTO.token.application.LocalUserJwtProvider;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.token.domain.BaseAccessToken;
import kr.co.picTO.token.domain.BaseTokenRepo;
import kr.co.picTO.member.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.member.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.member.dto.social.UserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocalUserService {
    private final BaseLocalUserRepo userRepository;
    private final BaseTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @Transactional
    public SingleResult<BaseAccessToken> login(@NotNull LocalUserLoginRequestDto localUserLoginRequestDto) {
        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        BaseLocalUser user = userRepository.findByEmail(localUserLoginRequestDto.toEntity().getEmail()).orElseThrow(CustomUserNotFoundException::new);
        if(!passwordEncoder.matches(localUserLoginRequestDto.toEntity().getPassword(), user.getPassword())) {
            throw new CustomEmailLoginFailedException();
        }

        BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user, user.getRoles());

        SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(baseAccessToken);
        loggingService.singleResultLogging(this.getClass(), "login", singleResult);

        return singleResult;
    }

    @Transactional
    public SingleResult<Long> signUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        log.info("Local User SVC Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        if(userRepository.findByEmail(localUserSignUpRequestDto.getEmail()).isPresent()) {
            throw new CustomUserExistException();
        }
        Long resultId = userRepository.save(localUserSignUpRequestDto.toEntity(passwordEncoder)).getId();
        log.info("Local User SVC SignUp resultId : " + resultId);

        SingleResult<Long> singleResult = responseService.getSingleResult(resultId);
        loggingService.singleResultLogging(this.getClass(), "SignUp", singleResult);

        return singleResult;
    }

    @Transactional(readOnly = true)
    public SingleResult<UserProfileResponseDto> getProfileLocal(String access_token) {
        log.info("Local User SVC Profile access_token : " + access_token);

        BaseAccessToken bat = tokenRepo.findByAccessToken(access_token).orElseThrow(CustomRefreshTokenException::new);
        BaseLocalUser user = userRepository.findById(bat.getBlu().getId()).orElseThrow(CustomUserNotFoundException::new);

        UserProfileResponseDto userProfileResponseDto = UserProfileResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickName())
                .profile_image_url(user.getProfile_image_url())
                .build();

        SingleResult<UserProfileResponseDto> singleResult = responseService.getSingleResult(userProfileResponseDto);
        loggingService.singleResultLogging(this.getClass(), "getProfileLocal", singleResult);

        return singleResult;
    }

    @Transactional
    public SingleResult<BaseAccessToken> reissue(BaseAccessToken bat) {
        if (!localUserJwtProvider.validationToken(bat.getRefresh_token())) {
            throw new CustomRefreshTokenException();
        } else {
            String accessToken = bat.getAccess_token();
            Authentication authentication = localUserJwtProvider.getAuthentication(accessToken);

            BaseLocalUser user = userRepository.findByEmail(authentication.getName()).orElseThrow(CustomUserNotFoundException::new);
            BaseAccessToken newRefreshToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user, user.getRoles());
            bat.refreshToken(newRefreshToken.toString());
            log.info("Local User SVC reissue newRefreshToken " + newRefreshToken);

            SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(newRefreshToken);
            loggingService.singleResultLogging(this.getClass(), "reissue", singleResult);

            return singleResult;
        }
    }

    @Transactional
    public SingleResult<Integer> deleteToken(String access_token) {
        Integer id = tokenRepo.deleteByAccessToken(access_token);
        log.info("Local User SVC deleteToken bat id : " + id);

        SingleResult<Integer> singleResult = responseService.getSingleResult(id);
        loggingService.singleResultLogging(this.getClass(), "deleteToken", singleResult);

        return singleResult;
    }
}
