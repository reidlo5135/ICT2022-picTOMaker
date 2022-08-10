package kr.co.picTO.member.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.member.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.member.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.member.dto.local.LocalTokenDto;
import kr.co.picTO.member.domain.oauth2.BaseAccessToken;
import kr.co.picTO.member.application.LocalUserService;
import kr.co.picTO.common.application.ResponseLoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"1. Local User"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/user")
public class LocalUserController {

    private static final String className = LocalUserController.class.toString();

    private final LocalUserService userService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "로그인", notes = "Login By Email")
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody LocalUserLoginRequestDto localUserLoginRequestDto) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());
        try {
            ett = userService.login(localUserLoginRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            log.info("Local User Controller Login ett : " + ett);
            return ett;
        }
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<?> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());
        try {
            ett = userService.signUp(localUserSignUpRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            log.info("Local User Controller SignUp ett : " + ett);
            return ett;
        }
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@RequestBody Map<String, String> access_token) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller access_token : " + access_token.get("access_token"));
        try {
            ett = userService.getProfileLocal(access_token.get("access_token"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            log.info("Local User Controller getProfile ett : " + ett);
            return ett;
        }
    }

    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody LocalTokenDto tokenDto) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller Reissue tokenDto : ", tokenDto.getAccessToken() + ", " + tokenDto.getRefreshToken());
        try {
            BaseAccessToken bat = tokenDto.toEntity(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
            log.info("Local User Controller Reissue bat : " + bat.getAccess_token() + ", " + bat.getRefresh_token());

            ett = userService.reissue(bat);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            log.info("Local User Controller Reissue ett : " + ett);
            return ett;
        }
    }

    @PostMapping(value = "/nickname")
    public ResponseEntity<?> getNickNameByEmail(@RequestBody Map<String, String> reqBody) {
        ResponseEntity<?> ett = null;
        String email = reqBody.get("email");
        log.info("Local User Controller getNickNameByEmail email : ", email);
        try {
            ett = userService.findNickNameByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller getNickNameByEmail error occurred : ", e.getMessage());
        } finally {
            log.info("Local User Controller getNickNameByEmail ett : " + ett);
            return ett;
        }
    }

    @DeleteMapping(value = "/token/invalid/{access_token}")
    public ResponseEntity<?> inValidToken(@PathVariable String access_token) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "inValidToken", access_token, "", "");
        try {
            ett = userService.deleteToken(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller inValidAndRefreshToken error occurred : ", e.getMessage());
        } finally {
            log.info("Local User Controller inValidToken ett : ", ett);
            return ett;
        }
    }
}
