package kr.co.picTO.controller;

import io.swagger.annotations.*;
import kr.co.picTO.config.security.JwtProvider;
import kr.co.picTO.dto.user.UserLoginResponseDTO;
import kr.co.picTO.dto.user.UserSignUpRequestDTO;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.ResponseService;
import kr.co.picTO.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. SignUp / Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserService userService;
    private final ResponseService responseService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원 검색")
    @GetMapping(value = "/login")
    public SingleResult<String> login(@ApiParam(value = "로그인 아이디 : 이메일", required = true) @RequestParam String email,
                                      @ApiParam(value = "로그인 비밀번호 : ", required = true) @RequestParam String password) {
        UserLoginResponseDTO userLoginDTO = userService.login(email, password);

        String token = jwtProvider.createToken(String.valueOf(userLoginDTO.getUserId()), userLoginDTO.getRoles());
        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/signup")
    public SingleResult<Long> signup(@ApiParam(value = "회원가입 아이디 : 이메일", required = true) @RequestParam String email,
                                     @ApiParam(value = "회원 가입 비밀번호 : ", required = true) @RequestParam String password,
                                     @ApiParam(value = "회원 가입 이름 : ", required = true) @RequestParam String name,
                                     @ApiParam(value = "회원 가입 닉네임", required = true) @RequestParam String nickName) {
        UserSignUpRequestDTO userSignUpRequestDTO = UserSignUpRequestDTO.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .build();

        Long signupId = userService.signup(userSignUpRequestDTO);
        return responseService.getSingleResult(signupId);
    }
}
