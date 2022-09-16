package kr.co.picto.user.presentation;

import io.swagger.annotations.*;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.token.dto.TokenRequestDto;
import kr.co.picTO.token.dto.TokenResponseDto;
import kr.co.picTO.user.application.local.UserService;
import kr.co.picTO.user.dto.local.UserCreateDto;
import kr.co.picTO.user.dto.local.UserInfoDto;
import kr.co.picTO.user.dto.local.UserLoginDto;
import kr.co.picTO.user.dto.local.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SingleResult<TokenResponseDto>> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody UserLoginDto userLoginDto) {
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
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 프로필 조회", notes = "프로필 조회")
    public ResponseEntity<SingleResult<UserInfoDto>> getProfile(@RequestHeader(value = "X-AUTH-TOKEN") String access_token) {
        return ResponseEntity.ok().body(userService.getProfileLocal(access_token));
    }

    /**
     * frontend - callback.js
     **/
    @PostMapping(value = "/reissue")
    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
    public ResponseEntity<SingleResult<TokenResponseDto>> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok().body(userService.reissue(tokenRequestDto));
    }

    /**
     *
     */
    @PutMapping
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 정보 수정", notes = "Update User Info")
    public ResponseEntity<SingleResult<UserInfoDto>> update(
            @ApiParam(value = "Token reissue DTO", required = true) @RequestBody UserUpdateDto userUpdateDto,
            @ApiParam(value = "Access Token", required = true) @RequestHeader(value = "X-AUTH-TOKEN") String access_token) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, access_token));
    }

    /**
     * frontend - MyPage.js
     **/
    @DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "Make User InActive", notes = "User DeActivate")
    public ResponseEntity<SingleResult<Integer>> deActivate(@ApiParam(value = "Access Token", required = true) @RequestHeader(value = "X-AUTH-TOKEN") String access_token) {
        userService.delete(access_token);
        return ResponseEntity.ok().build();
    }
}
