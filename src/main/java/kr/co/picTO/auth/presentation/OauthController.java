package kr.co.picTO.auth.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.picTO.auth.application.OauthService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.user.dto.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static kr.co.picTO.auth.advice.LoggedInInterceptor.createJWTCookie;

@Api(tags = {"10. Oauth Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth")
public class OauthController {
    private final OauthService oauthService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 로그인 후 token 발급", notes = "Generate Token After User Login ")
    @PostMapping("/login")
    public ResponseEntity<SingleResult<String>> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        response.addCookie(createJWTCookie(oauthService.login(userLoginDto).getToken()));

        return ResponseEntity.ok().body(responseService.getSingleResult(oauthService.login(userLoginDto).getToken()));
    }

    @ApiOperation(value = "발급 토큰 logout 처리", notes = "Modify Token Into Logout")
    @GetMapping("/logout")
    public ResponseEntity<SingleResult<String>> logout(HttpServletResponse response) {
        response.addCookie(createJWTCookie("logout"));

        return ResponseEntity.ok().build();
    }
}
