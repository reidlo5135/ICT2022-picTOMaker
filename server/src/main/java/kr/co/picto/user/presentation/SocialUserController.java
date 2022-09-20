package kr.co.picto.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.token.dto.SocialTokenResponseDto;
import kr.co.picto.user.application.social.SocialUserService;
import kr.co.picto.user.dto.social.SocialUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"2. OAuth2 User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/oauth2")
public class SocialUserController {
    private final SocialUserService socialUserService;

    /**
     * frontend - SocialUserCallback.js
     **/
    @PostMapping(value = "/login/{provider}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<SingleResult<SocialTokenResponseDto>> generateToken(@RequestHeader(value = "Authorization") String token, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.generateAccessToken(token, provider));
    }

    /**
     * frontend - TopProfile.js
     **/
    @PostMapping(value = "/info/{provider}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<SingleResult<SocialUserInfoDto>> getProfile(@RequestHeader(value = "Authorization") String access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(socialUserService.getProfile(access_token, provider));
    }
}
