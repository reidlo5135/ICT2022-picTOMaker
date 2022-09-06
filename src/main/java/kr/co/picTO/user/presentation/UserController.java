package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.domain.AccessToken;
import kr.co.picTO.token.dto.TokenRequestDto;
import kr.co.picTO.user.application.local.UserService;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.dto.local.UserCreateDto;
import kr.co.picTO.user.dto.local.UserInfoDto;
import kr.co.picTO.user.dto.local.UserLoginDto;
import kr.co.picTO.user.dto.local.UserUpdateDto;
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
    @PostMapping(value = "/login")
    @ApiOperation(value = "로그인", notes = "Login By Email")
    public ResponseEntity<SingleResult<AccessToken>> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.login(userLoginDto));
    }

    /**
     * frontend - SignUp.js
     **/
    @PostMapping(value = "/signup")
    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.signUp(userCreateDto));
    }

    /**
     * frontend - Profile.js
     **/
    @PostMapping(value = "/info")
    @ApiOperation(value = "회원 프로필 조회", notes = "프로필 조회")
    public ResponseEntity<SingleResult<UserInfoDto>> getProfile(@ApiParam(value = "Access Token", required = true) @RequestBody Map<String, String> access_token) {
        return ResponseEntity.ok().body(userService.getProfileLocal(access_token.get("access_token")));
    }

    /**
     * frontend - callback.js
     **/
    @PostMapping(value = "/reissue")
    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    public ResponseEntity<SingleResult<AccessToken>> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.reissue(tokenRequestDto.toEntity(tokenRequestDto.getAccessToken(), tokenRequestDto.getRefreshToken())));
    }

    /**
     *
     */
    @PutMapping("/{access_token}")
    @ApiOperation(value = "회원 정보 수정", notes = "Update User Info")
    public ResponseEntity<SingleResult<UserInfoDto>> update(
            @ApiParam(value = "Token reissue DTO", required = true) @RequestBody UserUpdateDto userUpdateDto,
            @ApiParam(value = "Access Token", required = true) @PathVariable String access_token) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, access_token));
    }

    /**
     * frontend - MyPage.js
     **/
    @DeleteMapping("/{access_token}")
    @ApiOperation(value = "Make User InActive", notes = "User DeActivate")
    public ResponseEntity<SingleResult<User>> deActivate(@ApiParam(value = "Access Token", required = true) @PathVariable String access_token) {
        return ResponseEntity.ok().body(userService.delete(access_token));
    }
}
