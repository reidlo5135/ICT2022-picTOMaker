package kr.co.picTO.service.user;

import kr.co.picTO.advice.exception.*;
import kr.co.picTO.config.security.JwtProvider;
import kr.co.picTO.dto.jwt.TokenDTO;
import kr.co.picTO.dto.jwt.TokenRequestDTO;
import kr.co.picTO.dto.sign.UserLoginRequestDTO;
import kr.co.picTO.dto.sign.UserSignUpRequestDTO;
import kr.co.picTO.entity.user.RefreshToken;
import kr.co.picTO.entity.user.User;
import kr.co.picTO.repository.RefreshTokenJpaRepo;
import kr.co.picTO.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    @Transactional
    public TokenDTO login(UserLoginRequestDTO userLoginRequestDto) {

        User user = userJpaRepo.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        TokenDTO tokenDto = jwtProvider.createTokenDto(user.getUserid(), user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.getUserid())
                .token(tokenDto.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public Long signup(UserSignUpRequestDTO userSignupDto) {
        if (userJpaRepo.findByEmail(userSignupDto.getEmail()).isPresent())
            throw new CEmailSignUpFailedException();
        return userJpaRepo.save(userSignupDto.toEntity(passwordEncoder)).getUserid();
    }

    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDto) {
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        User user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken = tokenJpaRepo.findByKey(user.getUserid())
                .orElseThrow(CRefreshTokenException::new);

        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        TokenDTO newCreatedToken = jwtProvider.createTokenDto(user.getUserid(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }
}
