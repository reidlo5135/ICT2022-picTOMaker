package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.dto.social.ProfileDTO;
import kr.co.picTO.dto.token.LocalTokenDto;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.local.LocalUserService;
import kr.co.picTO.service.response.ResponseLoggingService;
import kr.co.picTO.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<SingleResult<BaseAccessToken>> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody LocalUserLoginRequestDto localUserLoginRequestDto) {
        ResponseEntity<SingleResult<BaseAccessToken>> ett = null;
        log.info("Local User Controller Login localReqDto : " + localUserLoginRequestDto.getEmail() + ", " + localUserLoginRequestDto.getPassword());

        try {
            BaseAccessToken baseAccessToken = userService.login(localUserLoginRequestDto);
            log.info("Local User Controller Login Token : " + baseAccessToken);

            SingleResult<BaseAccessToken> result = responseService.getSingleResult(baseAccessToken);
            loggingService.singleResultLogging(className, "loginAndCreateToken", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller SingUp ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return ett;
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        ResponseEntity<SingleResult<Long>> ett = null;
        log.info("Local User Controller Sign localReqDto : " + localUserSignUpRequestDto.getEmail() + ", " + localUserSignUpRequestDto.getPassword());

        try {
            Long signUpId = userService.signUp(localUserSignUpRequestDto);

            SingleResult<Long> result = responseService.getSingleResult(signUpId);
            loggingService.singleResultLogging(className, "save", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller SingUp ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return ett;
    }

    @PostMapping(value = "/profile")
    public ResponseEntity<SingleResult<ProfileDTO>> getProfile(@RequestBody Map<String, String> access_token) {
        ResponseEntity<SingleResult<ProfileDTO>> ett = null;
        log.info("Local User Controller access_token : " + access_token.get("access_token"));

        try {
            ProfileDTO profileDTO = userService.getProfileLocal(access_token.get("access_token"));
            log.info("Local User Controller pDTO : " + profileDTO);

            SingleResult result = responseService.getSingleResult(profileDTO);
            loggingService.singleResultLogging(className, "getProfile", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return ett;
    }

    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    @PostMapping(value = "/reissue")
    public ResponseEntity<SingleResult<BaseAccessToken>> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody LocalTokenDto tokenDto) {
        ResponseEntity<SingleResult<BaseAccessToken>> ett = null;
        log.info("Local User Controller Reissue tokenDto : ", tokenDto.getAccessToken() + ", " + tokenDto.getRefreshToken());

        try {
            BaseAccessToken bat = tokenDto.toEntity(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
            log.info("Local User Controller Reissue bat : " + bat.getAccess_token() + ", " + bat.getRefresh_token());

            SingleResult<BaseAccessToken> result = responseService.getSingleResult(userService.reissue(bat));
            loggingService.singleResultLogging(className, "reissue", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller SingUp ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return ett;
    }

    @PostMapping(value = "/nickname")
    public ResponseEntity<SingleResult<String>> getNickNameByEmail(@RequestBody Map<String, String> reqBody) {
        ResponseEntity<SingleResult<String>> ett = null;
        String email = reqBody.get("email");
        log.info("Local User Controller getNickNameByEmail email : ", email);

        try {
            String nickName = userService.findNickNameByEmail(email);
            log.info("Local User Controller getNickNameByEmaiil nickName : ", nickName);

            SingleResult<String> result = responseService.getSingleResult(nickName);
            loggingService.singleResultLogging(className, "getNickNameByEmail", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller getNickNameByEmail ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller getNickNameByEmail error occurred : ", e.getMessage());
        }
        return ett;
    }

    @DeleteMapping(value = "/token/invalid/{access_token}")
    public ResponseEntity<SingleResult<Long>> inValidToken(@PathVariable String access_token) {
        ResponseEntity<SingleResult<Long>> ett = null;
        loggingService.httpPathStrLogging(className, "inValidToken", access_token, "");

        try {
            Integer id = userService.deleteToken(access_token);

            SingleResult result = responseService.getSingleResult(id);
            loggingService.singleResultLogging(className, "inValidToken", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("Local User Controller inValidToken ett : ", ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Local User Controller inValidAndRefreshToken error occurred : ", e.getMessage());
        }
        return ett;
    }
}
