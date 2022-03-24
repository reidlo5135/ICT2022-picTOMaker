package kr.co.picTO.service;

import kr.co.picTO.advice.exception.CEmailLoginFailedException;
import kr.co.picTO.advice.exception.CEmailSignUpFailedException;
import kr.co.picTO.advice.exception.CRefreshTokenException;
import kr.co.picTO.advice.exception.CUserNotFoundException;
import kr.co.picTO.config.security.JwtProvider;
import kr.co.picTO.dto.jwt.TokenDTO;
import kr.co.picTO.dto.jwt.TokenRequestDTO;
import kr.co.picTO.dto.sign.UserLoginRequestDTO;
import kr.co.picTO.dto.sign.UserSignUpRequestDTO;
import kr.co.picTO.entity.security.RefreshToken;
import kr.co.picTO.entity.user.User;
import kr.co.picTO.repository.RefreshTokenJpaRepo;
import kr.co.picTO.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenJpaRepo tokenJpaRepo;

    @Transactional
    public TokenDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        User user = userJpaRepo.findByEmail(userLoginRequestDTO.getEmail())
                .orElseThrow(CEmailLoginFailedException::new);

        if(!passwordEncoder.matches(userLoginRequestDTO.getPassword(), user.getPassword()))
            throw new CEmailLoginFailedException();

        TokenDTO tokenDTO = jwtProvider.createTokenDTO(user.getUserid(), user.getRoles());

        RefreshToken refreshToken = RefreshToken.builder()
                .key(user.getUserid())
                .token(tokenDTO.getRefreshToken())
                .build();
        tokenJpaRepo.save(refreshToken);
        return tokenDTO;
    }

    @Transactional
    public Long signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userJpaRepo.findByEmail(userSignUpRequestDTO.getEmail()).isPresent())
            throw new CEmailSignUpFailedException();
        return userJpaRepo.save(userSignUpRequestDTO.toEntity(passwordEncoder)).getUserid();
    }

    @Transactional
    public TokenDTO reissue(TokenRequestDTO tokenRequestDTO) {
        if(!jwtProvider.validationToken(tokenRequestDTO.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        String accessToken = tokenRequestDTO.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        User user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken = tokenJpaRepo.findByKey(user.getUserid())
                .orElseThrow(CRefreshTokenException::new);

        if(!refreshToken.getToken().equals(tokenRequestDTO.getRefreshToken()))
            throw new CRefreshTokenException();

        TokenDTO newCreatedToken = jwtProvider.createTokenDTO(user.getUserid(), user.getRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        tokenJpaRepo.save(updateRefreshToken);

        return newCreatedToken;
    }

}
