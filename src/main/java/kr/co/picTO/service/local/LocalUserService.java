package kr.co.picTO.service.local;

import kr.co.picTO.advice.exception.CustomCommunicationException;
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
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import kr.co.picTO.service.response.ResponseLoggingService;
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

    private static final String className = LocalUserService.class.toString();

    private final BaseLocalUserRepo userJpaRepo;
    private final BaseTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @Transactional
    public ResponseEntity<?> login(LocalUserLoginRequestDto localUserLoginRequestDto) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("Local User SVC Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        try {
            BaseLocalUser user = userJpaRepo.findByEmail(localUserLoginRequestDto.getEmail()).orElse(null);

            if(user == null) {
                CommonResult failResult = responseService.getFailResult(-1, "가입하지 않은 아이디입니다.");
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                loggingService.commonResultLogging(className, "login", failResult);
            } else if(!passwordEncoder.matches(localUserLoginRequestDto.getPassword(), user.getPassword())) {
                CommonResult failResult = responseService.getFailResult(-1, "비밀번호가 일치하지 않습니다.");
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                loggingService.commonResultLogging(className, "login", failResult);
            } else {
                BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());
                log.info("Local User SVC login bAToken : " + baseAccessToken);

                SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(baseAccessToken);
                loggingService.singleResultLogging(className, "login", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Local User SVC login ett : " + ett);
        return ett;
    }

    @Transactional
    public ResponseEntity<?> signUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("Local User SVC Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        try {
            if (userJpaRepo.findByEmail(localUserSignUpRequestDto.getEmail()) != null) {
                CommonResult failResult = responseService.getFailResult(-1, "이미 존재하는 회원입니다.");
                loggingService.commonResultLogging(className, "SignUp", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Long resultId = userJpaRepo.save(localUserSignUpRequestDto.toEntity(passwordEncoder)).getId();
                log.info("Local User SVC SignUp resultId : " + resultId);

                SingleResult<Long> singleResult = responseService.getSingleResult(resultId);
                loggingService.singleResultLogging(className, "SignUp", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Local User SVC SignUp ett : " + ett);
        return ett;
    }

    @Transactional
    public ResponseEntity<?> getProfileLocal(String access_token) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Local User SVC Profile access_token : " + access_token);

        try {
            BaseAccessToken bat = tokenRepo.findByAccessToken(access_token).orElse(null);
            BaseLocalUser user = userJpaRepo.findById(bat.getLocal_user_id()).orElse(null);

            log.info("Local User SVC Profile bat isNull : " + (bat == null));
            log.info("Local User SVC Profile user isNull : " + (user == null));

            if(bat != null && user != null) {
                ProfileDTO profileDTO = new ProfileDTO(user.getEmail(), user.getName(), user.getNickName(), user.getProfile_image_url());
                log.info("Local User SVC Profile pDTO isNull : " + (profileDTO == null));

                SingleResult<ProfileDTO> singleResult = responseService.getSingleResult(profileDTO);
                loggingService.singleResultLogging(className, "getProfileLocal", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            } else {
                CommonResult failResult = responseService.getFailResult(-1, "Get Profile Local Error Occurred");
                loggingService.commonResultLogging(className, "getProfileLocal", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Local User SVC Profile ett : " + ett);
        return ett;
    }

    @Transactional
    public ResponseEntity<?> reissue(BaseAccessToken bat) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Local User SVC reissue bat : " + bat);

        try {
            if (!localUserJwtProvider.validationToken(bat.getRefresh_token())) {
                throw new CustomRefreshTokenException();
            } else {
                String accessToken = bat.getAccess_token();
                Authentication authentication = localUserJwtProvider.getAuthentication(accessToken);

                log.info("Local User SVC reissue accToken, authen : " + accessToken + ", " + authentication);

                BaseLocalUser user = userJpaRepo.findByEmail(authentication.getName()).orElse(null);
                BaseAccessToken originToken = tokenRepo.findById(user.getId()).orElse(null);

                if(user != null && originToken != null) {
                    log.info("Local User SVC reissue user, refreshToken : " + user + ", " + originToken);

                    BaseAccessToken newRefreshToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());
                    bat.refreshToken(newRefreshToken.toString());
                    log.info("Local User SVC reissue newRefreshToken " + newRefreshToken);

                    SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(newRefreshToken);
                    loggingService.singleResultLogging(className, "reissue", singleResult);
                    ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
                } else {
                    CommonResult failResult = responseService.getFailResult(-1, "Reissue Error Occurred");
                    loggingService.commonResultLogging(className, "reissue", failResult);
                    ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Local User SVC login ett : " + ett);
        return ett;
    }

    public ResponseEntity<?> findNickNameByEmail(String email) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Local User SVC findNickNameByEmail email : " + email);

        try {
            BaseLocalUser user = userJpaRepo.findByEmail(email).orElse(null);

            if(user != null) {
                String nickName = user.getNickName();
                log.info("Local User SVC findNickNameByEmail nickName : ", nickName);

                SingleResult<String> singleResult = responseService.getSingleResult(nickName);
                loggingService.singleResultLogging(className, "findByNickNameByEmail", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            } else {
                CommonResult failResult = responseService.getFailResult(-1, "FindNickNameByEmail Error Occurred");
                loggingService.commonResultLogging(className, "findByNickNameByEmail", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Local User SVC findByNickNameByEmail ett : " + ett);
        return ett;
    }

    @Transactional
    public ResponseEntity<?> deleteToken(String access_token) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Local User SVC delT at : " + access_token);

        Integer id = null;
        try {
            id = userJpaRepo.deleteByAccessToken(access_token);
            log.info("Local User SVC delT bat id : " + id);

            if(id != 1) {
                SingleResult<Integer> singleResult = responseService.getSingleResult(id);
                loggingService.singleResultLogging(className, "deleteToken", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            } else {
                CommonResult failResult = responseService.getFailResult(-1, "DeleteToken Error Occurred");
                loggingService.commonResultLogging(className, "deleteToken", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC delT Error Occurred : " + e.getMessage());
            throw new CustomCommunicationException();
        }
        log.info("Local User SVC deleteToken ett : " + ett);
        return ett;
    }

    @Transactional(readOnly = true)
    public LocalUserResponseDto findById(Long id) {
        BaseLocalUser user = userJpaRepo.findById(id)
                .orElseThrow(CustomUserNotFoundException::new);
        return new LocalUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public LocalUserResponseDto findByEmail(String email) {
        BaseLocalUser user = userJpaRepo.findByEmail(email).orElse(null);
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
