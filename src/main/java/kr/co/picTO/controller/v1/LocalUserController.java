package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.dto.local.LocalUserLoginRequestDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;
import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.local.LocalUserService;
import kr.co.picTO.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Local User"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class LocalUserController {

    private final LocalUserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "Login By Email")
    @PostMapping(value = "/login")
    public SingleResult<BaseAccessToken> loginAndCreateToken(@ApiParam(value = "Login Req DTO", required = true) @RequestBody LocalUserLoginRequestDto localUserLoginRequestDto) {

        BaseAccessToken baseAccessToken = userService.login(localUserLoginRequestDto);

        SingleResult<BaseAccessToken> result = responseService.getSingleResult(baseAccessToken);

        log.info("Local User Controller login Token : " + baseAccessToken);
        log.info("Local User Controller login result : " + result);

        return result;
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public SingleResult<Long> save(@ApiParam(value = "Sign Req DTO", required = true) @RequestBody LocalUserSignUpRequestDto localUserSignUpRequestDto) {

        Long signUpId = userService.signUp(localUserSignUpRequestDto);

        SingleResult<Long> result = responseService.getSingleResult(signUpId);
        log.info("Local User Controller result : " + result);

        return result;
    }

//    @ApiOperation(value = "Access, Refresh Token Reissue", notes = "Token Reissue")
//    @PostMapping(value = "/reissue")
//    public SingleResult<TokenDto> reissue(@ApiParam(value = "Token reissue DTO", required = true) @RequestBody TokenRequestDto tokenRequestDto) {
//
//        SingleResult<TokenDto> result = responseService.getSingleResult(userService.reissue(tokenRequestDto));
//        log.info("Local User Controller reissue result : " + result);
//
//        return result;
//    }
}
