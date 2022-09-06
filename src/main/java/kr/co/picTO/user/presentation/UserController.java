package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.domain.AccessToken;
import kr.co.picTO.token.dto.TokenRequestDto;
import kr.co.picTO.user.application.local.UserService;
import kr.co.picTO.user.dto.local.UserLoginDto;
import kr.co.picTO.user.dto.local.UserInfoDto;
import kr.co.picTO.user.dto.local.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"1. Local User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/user")
public class UserController {
    private final UserService userService;

    /**
     * frontend - Main.js
     **/
    @ApiOperation(value = "로그인", notes = "Login By Email")
    @PostMapping(value = "/login")
    public ResponseEntity<SingleResult<AccessToken>> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.login(userLoginDto));
    }

    /**
     * frontend - SignUp.js
     **/
    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signup")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.signUp(userCreateDto));
    }

    /**
     * frontend - Profile.js
     **/
    @ApiOperation(value = "회원 프로필 조회", notes = "프로필 조회")
    @PostMapping(value = "/info")
    public ResponseEntity<SingleResult<UserInfoDto>> getProfile(@ApiParam(value = "Access Token", required = true) @RequestBody Map<String, String> access_token) {
        return ResponseEntity.ok().body(userService.getProfileLocal(access_token.get("access_token")));
    }

    /**
     * frontend - callback.js
     **/
    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    @PostMapping(value = "/reissue")
    public ResponseEntity<SingleResult<AccessToken>> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.reissue(tokenRequestDto.toEntity(tokenRequestDto.getAccessToken(), tokenRequestDto.getRefreshToken())));
    }

    /**
     *
     **/
    @DeleteMapping(value = "/{access_token}")
    public ResponseEntity<SingleResult<Integer>> inValidToken(@PathVariable String access_token) {
        return ResponseEntity.ok().body(userService.deleteToken(access_token));
    }
}
