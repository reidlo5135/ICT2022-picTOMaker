package kr.co.picTO.controller.v1;

import io.swagger.annotations.*;

import kr.co.picTO.advice.exception.CSocialAgreementException;
import kr.co.picTO.advice.exception.CUserNotFoundException;
import kr.co.picTO.config.security.JwtProvider;
import kr.co.picTO.dto.jwt.TokenDTO;
import kr.co.picTO.dto.jwt.TokenRequestDTO;
import kr.co.picTO.dto.sign.UserLoginRequestDTO;
import kr.co.picTO.dto.sign.UserSignUpRequestDTO;
import kr.co.picTO.dto.sign.UserSocialLoginRequestDTO;
import kr.co.picTO.dto.sign.UserSocialSignUpRequestDTO;
import kr.co.picTO.dto.social.KakaoProfile;
import kr.co.picTO.entity.user.User;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.repository.UserJpaRepo;
import kr.co.picTO.service.security.SignService;
import kr.co.picTO.service.response.ResponseService;

import kr.co.picTO.service.user.KakaoService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. SignUp / Login"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/sign")
public class SignController {

    private final JwtProvider jwtProvider;
    private final UserJpaRepo userJpaRepo;
    private final KakaoService kakaoService;
    private final SignService signService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인.")
    @PostMapping(value = "/login")
    public SingleResult<TokenDTO> login(@ApiParam(value = "로그인 요청", required = true)
                                        @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        TokenDTO tokenDTO = signService.login(userLoginRequestDTO);

        return responseService.getSingleResult(tokenDTO);
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입")
    @PostMapping(value = "/signup")
    public SingleResult<Long> signup(@ApiParam(value = "회원 가입 요청 DTO", required = true)
                                     @RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {

        Long signupId = signService.signup(userSignUpRequestDTO);
        return responseService.getSingleResult(signupId);
    }

    @ApiOperation(value = "액세스, 리프레시 토큰 재발급", notes = "액세스 토큰 만료시 회원 검증 후 리프레시 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급")
    @PostMapping(value = "/reissue")
    public SingleResult<TokenDTO> reissue(@ApiParam(value = "토큰 재발급 요청 DTO", required = true)
                                          @RequestBody TokenRequestDTO tokenRequestDTO) {
        return responseService.getSingleResult(signService.reissue(tokenRequestDTO));
    }

    @ApiOperation(
            value = "소셜 로그인 - kakao",
            notes = "카카오로 로그인을 합니다.")
    @PostMapping("/social/login/kakao")
    public SingleResult<TokenDTO> loginByKakao(
            @ApiParam(value = "소셜 로그인 dto", required = true)
            @RequestBody UserSocialLoginRequestDTO socialLoginRequestDto) {

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(socialLoginRequestDto.getAccessToken());
        if (kakaoProfile == null) throw new CUserNotFoundException();

        User user = userJpaRepo.findByEmailAndProvider(kakaoProfile.getKakao_account().getEmail(), "kakao")
                .orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtProvider.createTokenDTO(user.getUserid(), user.getRoles()));
    }

    @ApiOperation(
            value = "소셜 회원가입 - kakao",
            notes = "카카오로 회원가입을 합니다."
    )
    @PostMapping("/social/signup/kakao")
    public CommonResult signupBySocial(
            @ApiParam(value = "소셜 회원가입 dto", required = true)
            @RequestBody UserSocialSignUpRequestDTO socialSignupRequestDto) {

        KakaoProfile kakaoProfile =
                kakaoService.getKakaoProfile(socialSignupRequestDto.getAccessToken());
        if (kakaoProfile == null) throw new CUserNotFoundException();
        if (kakaoProfile.getKakao_account().getEmail() == null) {
            kakaoService.kakaoUnlink(socialSignupRequestDto.getAccessToken());
            throw new CSocialAgreementException();
        }

        Long userId = signService.socialSignup(UserSignUpRequestDTO.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(kakaoProfile.getProperties().getNickname())
                .nickName(kakaoProfile.getProperties().getNickname())
                .provider("kakao")
                .build());

        return responseService.getSingleResult(userId);
    }
}
