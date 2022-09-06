package kr.co.picTO.user.application.local;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.application.JwtProvider;
import kr.co.picTO.token.domain.AccessToken;
import kr.co.picTO.token.domain.AccessTokenRepository;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.dto.local.UserCreateDto;
import kr.co.picTO.user.dto.local.UserInfoDto;
import kr.co.picTO.user.dto.local.UserLoginDto;
import kr.co.picTO.user.exception.CustomEmailLoginFailedException;
import kr.co.picTO.user.exception.CustomRefreshTokenException;
import kr.co.picTO.user.exception.CustomUserExistException;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AccessTokenRepository tokenRepository;
    private final ResponseService responseService;

    @Transactional
    public SingleResult<AccessToken> login(@NotNull UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.toEntity().getEmail()).orElseThrow(CustomUserNotFoundException::new);
        if(!passwordEncoder.matches(userLoginDto.toEntity().getPassword(), user.getPassword())) {
            throw new CustomEmailLoginFailedException();
        }
        return responseService.getSingleResult(jwtProvider.createToken(String.valueOf(user.getId()), user, user.getRoles()));
    }

    @Transactional
    public SingleResult<Long> signUp(@NotNull UserCreateDto userCreateDto) {
        if(userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new CustomUserExistException();
        }

        return responseService.getSingleResult(userRepository.save(userCreateDto.toEntity(passwordEncoder)).getId());
    }

    @Transactional(readOnly = true)
    public SingleResult<UserInfoDto> getProfileLocal(String access_token) {
        AccessToken bat = tokenRepository.findByAccessToken(access_token).orElseThrow(CustomRefreshTokenException::new);
        User user = userRepository.findById(bat.getUser().getId()).orElseThrow(CustomUserNotFoundException::new);

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .profile_image_url(user.getProfile_image_url())
                .build();

        return responseService.getSingleResult(userInfoDto);
    }

    @Transactional
    public SingleResult<AccessToken> reissue(AccessToken bat) {
        if (!jwtProvider.validationToken(bat.getRefresh_token())) {
            throw new CustomRefreshTokenException();
        } else {
            String accessToken = bat.getAccess_token();
            Authentication authentication = jwtProvider.getAuthentication(accessToken);

            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(CustomUserNotFoundException::new);
            AccessToken newRefreshToken = jwtProvider.createToken(String.valueOf(user.getId()), user, user.getRoles());
            bat.refreshToken(newRefreshToken.toString());

            return responseService.getSingleResult(newRefreshToken);
        }
    }

    @Transactional
    public SingleResult<Integer> deleteToken(String access_token) {
        return responseService.getSingleResult(tokenRepository.deleteByAccessToken(access_token));
    }
}
