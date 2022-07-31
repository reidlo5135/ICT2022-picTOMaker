package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.*;
import kr.co.picTO.config.security.LocalUserJwtProvider;
import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserRequestDto;
import kr.co.picTO.dto.local.LocalUserResponseDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import kr.co.picTO.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocalUserService {

    private final BaseLocalUserRepo userJpaRepo;
    private final BaseTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;
    private final ResponseService responseService;

    @Transactional
    public ResponseEntity<?> login(LocalUserLoginRequestDto localUserLoginRequestDto) {
        ResponseEntity<?> ett = null;

        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        BaseLocalUser user = userJpaRepo.findByEmail(localUserLoginRequestDto.getEmail())
                .orElseThrow(CustomEmailLoginFailedException::new);

        if (!passwordEncoder.matches(localUserLoginRequestDto.getPassword(), user.getPassword()))
            throw new CustomEmailLoginFailedException();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if(user != null){
            BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());
            log.info("Local User SVC Login bAToken : " + baseAccessToken);

            SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(baseAccessToken);
            log.info("Local User SVC login singleResult : " + singleResult);
            ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            log.info("Local User SVC login ett : " + ett);
        } else {
            CommonResult failResult = responseService.getFailResult(-1, "회원정보가 존재하지 않습니다.");
            ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Local User SVC login failResult : " + failResult);
        }
        return ett;
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
        BaseLocalUser user = userJpaRepo.findById(bat.getLocal_user_id()).orElseThrow(CustomUserNotFoundException::new);

        log.info("Local User SVC Profile bat isNull : " + (bat == null));
        log.info("Local User SVC Profile user isNull : " + (user == null));

        ProfileDTO profileDTO = new ProfileDTO(user.getEmail(), user.getName(), user.getNickName(), user.getProfile_image_url());
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

    public String findNickNameByEmail(String email) {
        log.info("Local User SVC findNickNameByEmail email : " + email);

        BaseLocalUser user = userJpaRepo.findByEmail(email)
                .orElseThrow(CustomUserNotFoundException::new);
        String nickName = user.getNickName();
        log.info("Local User SVC findNickNameByEmail nickName : ", nickName);

        return nickName;
    }

    @Transactional
    public Integer deleteToken(String access_token) {
        log.info("Local User SVC delT at : " + access_token);
        Integer id = null;
        try {
            id = userJpaRepo.deleteByAccessToken(access_token);
            log.info("Local User SVC delT bat id : " + id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC delT Error Occurred : " + e.getMessage());
            throw new CustomCommunicationException();
        }
        return id;
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
