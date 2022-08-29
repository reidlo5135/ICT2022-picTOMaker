package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.user.application.local.LocalUserService;
import kr.co.picTO.token.dto.TokenRequestDto;
import kr.co.picTO.user.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.user.dto.local.LocalUserSignUpRequestDto;
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
    private final String ClassName = this.getClass().getName();
    private final LocalUserService userService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "로그인", notes = "Login By Email")
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody LocalUserLoginRequestDto localUserLoginRequestDto) {
        return ResponseEntity.ok().body(userService.login(localUserLoginRequestDto));
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public ResponseEntity<?> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody LocalUserSignUpRequestDto localUserSignUpRequestDto) {
        return ResponseEntity.ok().body(userService.signUp(localUserSignUpRequestDto));
    }

    @ApiOperation(value = "회원 프로필 조회", notes = "프로필 조회")
    @PostMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@ApiParam(value = "Access Token", required = true) @RequestBody Map<String, String> access_token) {
        return ResponseEntity.ok().body(userService.getProfileLocal(access_token.get("access_token")));
    }

    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.reissue(tokenRequestDto.toEntity(tokenRequestDto.getAccessToken(), tokenRequestDto.getRefreshToken())));
    }

    @DeleteMapping(value = "/token/{access_token}")
    public ResponseEntity<?> inValidToken(@PathVariable String access_token) {
        loggingService.httpPathStrLogging(ClassName, "inValidToken", access_token, "", "");
        return ResponseEntity.ok().body(userService.deleteToken(access_token));
    }
}
