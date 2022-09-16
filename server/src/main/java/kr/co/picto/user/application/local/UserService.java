package kr.co.picto.user.application.local;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.application.JwtProvider;
import kr.co.picTO.token.domain.AccessTokenRepository;
import kr.co.picTO.token.domain.RefreshToken;
import kr.co.picTO.token.domain.RefreshTokenRepository;
import kr.co.picTO.token.dto.TokenRequestDto;
import kr.co.picTO.token.dto.TokenResponseDto;
import kr.co.picTO.user.domain.AccountStatus;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.dto.local.UserCreateDto;
import kr.co.picTO.user.dto.local.UserInfoDto;
import kr.co.picTO.user.dto.local.UserLoginDto;
import kr.co.picTO.user.dto.local.UserUpdateDto;
import kr.co.picTO.user.exception.CustomEmailLoginFailedException;
import kr.co.picTO.user.exception.CustomRefreshTokenException;
import kr.co.picTO.user.exception.CustomUserExistException;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author reidlo
 * 2022-09-16
 * ver 1.1.1
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AccessTokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResponseService responseService;

    @Transactional
    public SingleResult<TokenResponseDto> login(@Valid UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
        checkStatus(user);
        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomEmailLoginFailedException();
        }
        TokenResponseDto tokenResponseDto = jwtProvider.createToken(user.getId(), user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
                .tokenId(user.getId())
                .token(tokenResponseDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return responseService.getSingleResult(tokenResponseDto);
    }

    @Transactional
    public SingleResult<Long> signUp(@Valid UserCreateDto userCreateDto) {
        if(userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new CustomUserExistException();
        }

        return responseService.getSingleResult(userRepository.save(userCreateDto.toEntity(passwordEncoder)).getId());
    }

    @Transactional(readOnly = true)
    public SingleResult<UserInfoDto> getProfileLocal(String access_token) {
        User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .profile_image_url(user.getProfile_image_url())
                .build();

        return responseService.getSingleResult(userInfoDto);
    }

    @Transactional
    public SingleResult<UserInfoDto> update(UserUpdateDto userUpdateDto, String access_token) {
        User findUser = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);
        findUser.update(userUpdateDto.toEntity());
        findUser.activate();

        return responseService.getSingleResult(UserInfoDto.from(findUser));
    }

    @Transactional
    public SingleResult<TokenResponseDto> reissue(TokenRequestDto tokenRequestDto) {
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomRefreshTokenException();
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        User user = userRepository.findById(Long.parseLong(authentication.getName())).orElseThrow(CustomUserNotFoundException::new);
        RefreshToken refreshToken = refreshTokenRepository.findByTokenId(user.getId()).orElseThrow(CustomRefreshTokenException::new);

        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) throw new CustomRefreshTokenException();

        TokenResponseDto refreshedToken = jwtProvider.createToken(user.getId(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(refreshedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return responseService.getSingleResult(refreshedToken);
    }

    @Transactional
    public void delete(String access_token) {
        User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);
        user.deactivate();
    }

    private void checkStatus(User user) {
        if(user.getStatus() == AccountStatus.INACTIVE) throw new CustomUserNotFoundException();
    }
}
