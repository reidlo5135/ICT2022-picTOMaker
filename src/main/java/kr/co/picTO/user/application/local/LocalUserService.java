package kr.co.picTO.user.application.local;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.application.LocalUserJwtProvider;
import kr.co.picTO.token.domain.BaseAccessToken;
import kr.co.picTO.token.domain.BaseTokenRepo;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.user.dto.local.LocalUserProfileResponseDto;
import kr.co.picTO.user.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.user.exception.CustomEmailLoginFailedException;
import kr.co.picTO.user.exception.CustomRefreshTokenException;
import kr.co.picTO.user.exception.CustomUserExistException;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
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

    @Transactional
    public SingleResult<BaseAccessToken> login(@NotNull LocalUserLoginRequestDto localUserLoginRequestDto) {
        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        BaseLocalUser user = userRepository.findByEmail(localUserLoginRequestDto.toEntity().getEmail()).orElseThrow(CustomUserNotFoundException::new);
        if(!passwordEncoder.matches(localUserLoginRequestDto.toEntity().getPassword(), user.getPassword())) {
            throw new CustomEmailLoginFailedException();
        }
        return responseService.getSingleResult(localUserJwtProvider.createToken(String.valueOf(user.getId()), user, user.getRoles()));
    }

    @Transactional
    public SingleResult<Long> signUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        log.info("Local User SVC Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        if(userRepository.findByEmail(localUserSignUpRequestDto.getEmail()).isPresent()) {
            throw new CustomUserExistException();
        }

        return responseService.getSingleResult(userRepository.save(localUserSignUpRequestDto.toEntity(passwordEncoder)).getId());
    }

    @Transactional(readOnly = true)
    public SingleResult<LocalUserProfileResponseDto> getProfileLocal(String access_token) {
        log.info("Local User SVC Profile access_token : " + access_token);

        BaseAccessToken bat = tokenRepo.findByAccessToken(access_token).orElseThrow(CustomRefreshTokenException::new);
        BaseLocalUser user = userRepository.findById(bat.getBlu().getId()).orElseThrow(CustomUserNotFoundException::new);

        LocalUserProfileResponseDto localUserProfileResponseDto = LocalUserProfileResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .profile_image_url(user.getProfile_image_url())
                .build();

        return responseService.getSingleResult(localUserProfileResponseDto);
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

            return responseService.getSingleResult(newRefreshToken);
        }
    }

    @Transactional
    public SingleResult<Integer> deleteToken(String access_token) {
        return responseService.getSingleResult(tokenRepo.deleteByAccessToken(access_token));
    }
}
