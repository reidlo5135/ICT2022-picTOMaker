package kr.co.picTO.controller;

import io.swagger.annotations.*;

import kr.co.picTO.dto.jwt.TokenDTO;
import kr.co.picTO.dto.jwt.TokenRequestDTO;
import kr.co.picTO.dto.sign.UserLoginRequestDTO;
import kr.co.picTO.dto.sign.UserSignUpRequestDTO;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.SecurityService;
import kr.co.picTO.service.response.ResponseService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. SignUp / Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/sign")
public class SignController {

    private final SecurityService securityService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인.")
    @PostMapping(value = "/login")
    public SingleResult<TokenDTO> login(@ApiParam(value = "로그인 요청", required = true)
                                        @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        TokenDTO tokenDTO = securityService.login(userLoginRequestDTO);

        return responseService.getSingleResult(tokenDTO);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입")
    @PostMapping(value = "/signup")
    public SingleResult<Long> signup(@ApiParam(value = "회원 가입 요청 DTO", required = true)
                                     @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {

        Long signupId = securityService.signup(userSignUpRequestDTO);
        return responseService.getSingleResult(signupId);
    }

    @ApiOperation(value = "액세스, 리프레시 토큰 재발급", notes = "액세스 토큰 만료시 회원 검증 후 리프레시 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급")
    @PostMapping(value = "/reissue")
    public SingleResult<TokenDTO> reissue(@ApiParam(value = "토큰 재발급 요청 DTO", required = true)
                                          @RequestBody TokenRequestDTO tokenRequestDTO) {
        return responseService.getSingleResult(securityService.reissue(tokenRequestDTO));
    }
}
