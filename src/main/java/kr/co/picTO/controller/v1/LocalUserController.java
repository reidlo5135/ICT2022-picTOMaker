package kr.co.picTO.controller.v1;

import io.swagger.annotations.*;

import kr.co.picTO.config.security.LocalUserJwtProvider;
import kr.co.picTO.dto.local.LocalUserLoginResponseDto;
import kr.co.picTO.dto.local.LocalUserSignUpRequestDto;

import kr.co.picTO.model.response.SingleResult;

import kr.co.picTO.service.local.LocalUserService;
import kr.co.picTO.service.response.ResponseService;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. Local User"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class LocalUserController {

    private final LocalUserService userService;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final LocalUserJwtProvider localUserJwtProvider;

    @ApiOperation(value = "로그인", notes = "Login By Email")
    @GetMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "Login Email : email", required = true) @RequestParam String email,
                                      @ApiParam(value = "Login Pwd : ", required = true) @RequestParam String password) {

        LocalUserLoginResponseDto localUserLoginResponseDto = userService.login(email, password);

        String token = localUserJwtProvider.createToken(String.valueOf(localUserLoginResponseDto.getId()), localUserLoginResponseDto.getRoles());

        SingleResult<String> result = responseService.getSingleResult(token);

        log.info("Local User Controller login Token : " + token);
        log.info("Local User Controller login result : " + result);

        return result;
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signUp")
    public SingleResult<Long> save(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                   @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
                                   @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                                   @ApiParam(value = "회원 닉네임", required = true) @RequestParam String nickname) {
        LocalUserSignUpRequestDto user = LocalUserSignUpRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickname)
                .build();

        log.info("Local User Controller signUp DTO : " + user.toString());

        SingleResult<Long> result = responseService.getSingleResult(userService.singUp(user));
        log.info("Local User Controller result : " + result);

        return result;
    }
}
