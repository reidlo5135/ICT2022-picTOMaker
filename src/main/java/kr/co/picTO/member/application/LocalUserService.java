package kr.co.picTO.member.application;

import kr.co.picTO.common.exception.CustomRefreshTokenException;
import kr.co.picTO.member.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.member.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.member.dto.social.UserProfileResponseDto;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAccessToken;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.member.domain.BaseTokenRepo;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
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

@Log4j2
@Service
@RequiredArgsConstructor
public class LocalUserService {
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
                loggingService.commonResultLogging(this.getClass(), "login", failResult);
            } else if(!passwordEncoder.matches(localUserLoginRequestDto.getPassword(), user.getPassword())) {
                CommonResult failResult = responseService.getFailResult(-1, "비밀번호가 일치하지 않습니다.");
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                loggingService.commonResultLogging(this.getClass(), "login", failResult);
            } else {
                BaseAccessToken baseAccessToken = localUserJwtProvider.createToken(String.valueOf(user.getId()), user.getId(), user.getRoles());
                log.info("Local User SVC login bAToken : " + baseAccessToken);

                SingleResult<BaseAccessToken> singleResult = responseService.getSingleResult(baseAccessToken);
                loggingService.singleResultLogging(this.getClass(), "login", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC login Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC login ett : " + ett);
            return ett;
        }

    }

    @Transactional
    public ResponseEntity<?> signUp(LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("Local User SVC Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        BaseLocalUser user = userJpaRepo.findByEmail(localUserSignUpRequestDto.getEmail()).orElse(null);
        log.info("Local User SVC SignUp user : " + user);
        try {
            if (user != null) {
                CommonResult failResult = responseService.getFailResult(-1, "이미 존재하는 회원입니다.");
                loggingService.commonResultLogging(this.getClass(), "SignUp", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Long resultId = userJpaRepo.save(localUserSignUpRequestDto.toEntity(passwordEncoder)).getId();
                log.info("Local User SVC SignUp resultId : " + resultId);

                SingleResult<Long> singleResult = responseService.getSingleResult(resultId);
                loggingService.singleResultLogging(this.getClass(), "SignUp", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC SignUp Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC SignUp ett : " + ett);
            return ett;
        }
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
                UserProfileResponseDto userProfileResponseDto = new UserProfileResponseDto(user.getEmail(), user.getName(), user.getNickName(), user.getProfile_image_url());
                log.info("Local User SVC Profile pDTO isNull : " + (userProfileResponseDto == null));

                SingleResult<UserProfileResponseDto> singleResult = responseService.getSingleResult(userProfileResponseDto);
                loggingService.singleResultLogging(this.getClass(), "getProfileLocal", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            } else {
                CommonResult failResult = responseService.getFailResult(-1, "Get Profile Local Error Occurred");
                loggingService.commonResultLogging(this.getClass(), "getProfileLocal", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC Profile Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC Profile ett : " + ett);
            return ett;
        }
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
                    loggingService.singleResultLogging(this.getClass(), "reissue", singleResult);
                    ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
                } else {
                    CommonResult failResult = responseService.getFailResult(-1, "Reissue Error Occurred");
                    loggingService.commonResultLogging(this.getClass(), "reissue", failResult);
                    ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC reissue Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC reissue ett : " + ett);
            return ett;
        }
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
                loggingService.singleResultLogging(this.getClass(), "findByNickNameByEmail", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            } else {
                CommonResult failResult = responseService.getFailResult(-1, "FindNickNameByEmail Error Occurred");
                loggingService.commonResultLogging(this.getClass(), "findByNickNameByEmail", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC findByNickNameByEmail Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC findByNickNameByEmail ett : " + ett);
            return ett;
        }
    }

    @Transactional
    public ResponseEntity<?> deleteToken(String access_token) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("Local User SVC deleteToken at : " + access_token);

        try {
            if(access_token == null || access_token.equals("")) {
                CommonResult failResult = responseService.getFailResult(-1, "Local DeleteToken Error Occurred");
                loggingService.commonResultLogging(this.getClass(), "deleteToken", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Integer id = tokenRepo.deleteByAccessToken(access_token);
                log.info("Local User SVC deleteToken bat id : " + id);

                SingleResult<Integer> singleResult = responseService.getSingleResult(id);
                loggingService.singleResultLogging(this.getClass(), "deleteToken", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User SVC deleteToken Error Occurred : " + e.getMessage());
        } finally {
            log.info("Local User SVC deleteToken ett : " + ett);
            return ett;
        }
    }
}
