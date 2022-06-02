package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.CustomEmailLoginFailedException;
import kr.co.picTO.advice.exception.CustomEmailSignUpFailedException;
import kr.co.picTO.advice.exception.CustomRefreshTokenException;
import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.config.security.LocalUserJwtProvider;
import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserRequestDto;
import kr.co.picTO.dto.local.LocalUserResponseDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
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
    private final BaseTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;

    @Transactional
    public BaseAccessToken login(LocalUserLoginRequestDto localUserLoginRequestDto) {

        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        BaseLocalUser user = userJpaRepo.findByEmail(localUserLoginRequestDto.getEmail())
                .orElseThrow(CustomEmailLoginFailedException::new);

        if (!passwordEncoder.matches(localUserLoginRequestDto.getPassword(), user.getPassword()))
            throw new CustomEmailLoginFailedException();

        BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());

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

    @Transactional
    public ProfileDTO getProfileLocal(String access_token) {
        log.info("Local User SVC Profile access_token : " + access_token);

        BaseAccessToken bat = tokenRepo.findByAccessToken(access_token).orElseThrow(CustomUserNotFoundException::new);
        BaseLocalUser user = userJpaRepo.findById(bat.getUser_id()).orElseThrow(CustomUserNotFoundException::new);

        log.info("Local User SVC Profile bat isNull : " + (bat == null));
        log.info("Local User SVC Profile user isNull : " + (user == null));

        ProfileDTO profileDTO = new ProfileDTO(user.getEmail(), user.getNickName(), user.getProfile_image_url());
        log.info("Local User SVC Profile pDTO isNull : " + (profileDTO == null));

        return profileDTO;
    }

    @Transactional
    public BaseAccessToken reissue(BaseAccessToken bat) {

        if (!localUserJwtProvider.validationToken(bat.getRefresh_token())) {
            throw new CustomRefreshTokenException();
        }

        String accessToken = bat.getAccess_token();
        Authentication authentication = localUserJwtProvider.getAuthentication(accessToken);

        log.info("Local User SVC reissue accToken, authen : " + accessToken + ", " + authentication);

        BaseLocalUser user = userJpaRepo.findByEmail(authentication.getName())
                .orElseThrow(CustomUserNotFoundException::new);

        BaseAccessToken originToken = tokenRepo.findById(user.getId()).orElseThrow(CustomRefreshTokenException::new);

        log.info("Local User SVC reissue user, refreshToken : " + user + ", " + originToken);

        BaseAccessToken newRefreshToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());
        bat.refreshToken(newRefreshToken.toString());

        log.info("Local User SVC reissue newRefreshToken " + newRefreshToken);

        return newRefreshToken;
    }

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
