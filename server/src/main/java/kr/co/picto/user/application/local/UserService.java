package kr.co.picto.user.application.local;

import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.application.JwtProvider;
import kr.co.picto.token.domain.RefreshToken;
import kr.co.picto.token.domain.RefreshTokenRepository;
import kr.co.picto.token.dto.TokenRequestDto;
import kr.co.picto.token.dto.TokenResponseDto;
import kr.co.picto.user.domain.AccountStatus;
import kr.co.picto.user.domain.local.User;
import kr.co.picto.user.domain.local.UserRepository;
import kr.co.picto.user.dto.local.UserCreateDto;
import kr.co.picto.user.dto.local.UserInfoDto;
import kr.co.picto.user.dto.local.UserLoginDto;
import kr.co.picto.user.dto.local.UserUpdateDto;
import kr.co.picto.user.exception.CustomEmailLoginFailedException;
import kr.co.picto.user.exception.CustomRefreshTokenException;
import kr.co.picto.user.exception.CustomUserExistException;
import kr.co.picto.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
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
    public SingleResult<UserInfoDto> info(String access_token) {
        User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(UserInfoDto.from(user));
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
        log.info("Reissue at : " + tokenRequestDto.getAccessToken());
        log.info("Reissue rt : " + tokenRequestDto.getRefreshToken());
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
    public SingleResult<Boolean> isValid(String access_token) {
        return responseService.getSingleResult(jwtProvider.validationToken(access_token));
    }

    @Transactional
    public void delete(String access_token) {
        User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);
        user.deactivate();
    }

    @Transactional
    public void logoutAndDeleteToken(String access_token) {
        User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);
        refreshTokenRepository.deleteByTokenId(user.getId());
    }

    private void checkStatus(User user) {
        if(user.getStatus() == AccountStatus.INACTIVE) throw new CustomUserNotFoundException();
    }
}
