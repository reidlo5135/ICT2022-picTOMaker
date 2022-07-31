package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.dto.token.LocalTokenDto;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.service.local.LocalUserService;
import kr.co.picTO.service.response.ResponseLoggingService;
import kr.co.picTO.service.response.ResponseService;
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
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "로그인", notes = "Login By Email")
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody LocalUserLoginRequestDto localUserLoginRequestDto) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        try {
            ett = userService.login(localUserLoginRequestDto);
            log.info("Local User Controller SingUp ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return ett;
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<?> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        try {
            ett = userService.signUp(localUserSignUpRequestDto);
            log.info("Local User Controller SignUp ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@RequestBody Map<String, String> access_token) {
        ResponseEntity<?> ett = null;
        log.info("Local User Controller access_token : " + access_token.get("access_token"));

        try {
            ett = userService.getProfileLocal(access_token.get("access_token"));
            log.info("Local User Controller getProfile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
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
            log.info("Local User Controller Reissue ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/nickname")
    public ResponseEntity<?> getNickNameByEmail(@RequestBody Map<String, String> reqBody) {
        ResponseEntity<?> ett = null;
        String email = reqBody.get("email");
        log.info("Local User Controller getNickNameByEmail email : ", email);

        try {
            ett = userService.findNickNameByEmail(email);
            log.info("Local User Controller getNickNameByEmail ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller getNickNameByEmail error occurred : ", e.getMessage());
        }
        return ett;
    }

    @DeleteMapping(value = "/token/invalid/{access_token}")
    public ResponseEntity<?> inValidToken(@PathVariable String access_token) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "inValidToken", access_token, "", "");

        try {
            ett = userService.deleteToken(access_token);
            log.info("Local User Controller inValidToken ett : ", ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller inValidAndRefreshToken error occurred : ", e.getMessage());
        }
        return ett;
    }
}
