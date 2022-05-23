package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.CustomEmailLoginFailedException;
import kr.co.picTO.advice.exception.CustomEmailSignUpFailedException;
import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.config.security.LocalUserJwtProvider;
import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserRequestDto;
import kr.co.picTO.dto.local.LocalUserResponseDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.repository.BaseLocalUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocalUserService {

    private final BaseLocalUserRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;

    @Transactional
    public BaseAccessToken login(LocalUserLoginRequestDto localUserLoginRequestDto) {

        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        BaseLocalUser user = userJpaRepo.findByEmail(localUserLoginRequestDto.getEmail())
                .orElseThrow(CustomEmailLoginFailedException::new);

        if (!passwordEncoder.matches(localUserLoginRequestDto.getPassword(), user.getPassword()))
            throw new CustomEmailLoginFailedException();

        BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getRoles());

        log.info("Local User SVC Login bAToken : " + baseAccessToken);

        return baseAccessToken;
    }

    @Transactional
    public Long signUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        log.info("Local User SVC Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());
        if (userJpaRepo.findByEmail(localUserSignUpRequestDto.getEmail()).isPresent())
            throw new CustomEmailSignUpFailedException();

        return userJpaRepo.save(localUserSignUpRequestDto.toEntity(passwordEncoder)).getId();
    }

//    @Transactional
//    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
//
//        if (!localUserJwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
//            throw new CRefreshTokenException();
//        }
//
//        String accessToken = tokenRequestDto.getAccessToken();
//        Authentication authentication = localUserJwtProvider.getAuthentication(accessToken);
//
//        log.info("Local User SVC accToken, authen : " + accessToken + ", " + authentication);
//
//        BaseLocalUser user = userJpaRepo.findById(Long.parseLong(authentication.getName()))
//                .orElseThrow(CUserNotFoundException::new);
//
//        BaseLocalRefreshToken refreshToken = baseRefreshTokenRepo.findByKey(user.getId())
//                .orElseThrow(CRefreshTokenException::new);
//
//        log.info("Local User SVC user, refreshToken : " + user + ", " + refreshToken);
//
//        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
//            throw new CRefreshTokenException();
//
//        TokenDto newCreatedToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getRoles());
//
//        BaseLocalRefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
//        baseRefreshTokenRepo.save(updateRefreshToken);
//
//        log.info("Local User SVC newCreatedToken, updateRefreshToken : " + newCreatedToken + ", " + updateRefreshToken);
//
//        return newCreatedToken;
//    }

    @Transactional(readOnly = true)
    public LocalUserResponseDto findById(Long id) {
        BaseLocalUser user = userJpaRepo.findById(id)
                .orElseThrow(CustomUserNotFoundException::new);
        return new LocalUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public LocalUserResponseDto findByEmail(String email) {
        BaseLocalUser user = userJpaRepo.findByEmail(email)
                .orElseThrow(CustomUserNotFoundException::new);
        return new LocalUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<LocalUserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(LocalUserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, LocalUserRequestDto userRequestDto) {
        BaseLocalUser modifiedUser = userJpaRepo
                .findById(id).orElseThrow(CustomUserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickName());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
