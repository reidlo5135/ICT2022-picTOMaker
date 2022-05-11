package kr.co.picTO.controller.v1;

import io.swagger.annotations.*;
import kr.co.picTO.dto.local.LocalUserRequestDto;
import kr.co.picTO.dto.local.LocalUserResponseDto;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.local.LocalUserService;
import kr.co.picTO.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Only Admin - User"})
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/admin")
public class AdminController {

    private final LocalUserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/{userId}")
    public SingleResult<LocalUserResponseDto> findUserById
            (@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {

        LocalUserResponseDto localUserResponseDto = userService.findById(userId);
        log.info("Admin Controller read by Id : " + localUserResponseDto);

        return responseService.getSingleResult(localUserResponseDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/{email}")
    public SingleResult<LocalUserResponseDto> findUserByEmail
            (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email,
             @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {

        LocalUserResponseDto localUserResponseDto = userService.findByEmail(email);
        log.info("Admin Controller read by Email : " + localUserResponseDto);

        return responseService.getSingleResult(localUserResponseDto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ListResult<LocalUserResponseDto> findAllUser() {

        List<LocalUserResponseDto> list = userService.findAllUser();
        log.info("Admin Controller read Entire List : " + list);

        return responseService.getListResult(list);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정합니다.")
    @PutMapping("/user")
    public SingleResult<Long> update (
            @ApiParam(value = "회원 ID", required = true) @RequestParam Long userId,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String nickName) {
        LocalUserRequestDto userRequestDto = LocalUserRequestDto.builder()
                .nickName(nickName)
                .build();

        Long result = userService.update(userId, userRequestDto);
        log.info("Admin Controller DTO, result : " + userRequestDto + ", " + result);

        return responseService.getSingleResult(result);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult();
    }
}
