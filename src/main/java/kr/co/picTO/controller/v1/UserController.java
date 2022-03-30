package kr.co.picTO.controller.v1;

import io.swagger.annotations.*;

import kr.co.picTO.dto.user.UserRequestDTO;
import kr.co.picTO.dto.user.UserResponseDTO;
import kr.co.picTO.model.response.CommonResult;
import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;

import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userID로 회원 조회")
    @GetMapping(value = "/user/{userId}")
    public SingleResult<UserResponseDTO> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId,
                                                      @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService
                .getSingleResult(userService.findById(userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 검색(이메일)", notes = "이메일로 회원 검색.")
    @GetMapping(value = "/user/email/{email}")
    public SingleResult<UserResponseDTO> findUserByEmail(@ApiParam(value = "회원 이메일", required = true) @PathVariable String email,
                                                         @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userService.findByEmail(email));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원 목록을 조회")
    @GetMapping(value = "/users")
    public ListResult<UserResponseDTO> findAllUser() {
        return responseService.getListResult(userService.findAllUser());
    }

    @ApiOperation(value = "회원 등록", notes = "회원 가입")
    @PostMapping(value = "/user")
    public SingleResult<Long> save(@ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        UserRequestDTO user = UserRequestDTO.builder()
                .email(email)
                .name(name)
                .build();

        return responseService.getSingleResult(userService.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보 수정")
    @PutMapping(value = "/user")
    public SingleResult<Long> update(@ApiParam(value = "회원 아이디", required = true) @RequestParam Long userId,
                                     @ApiParam(value = "회원 이메일", required = true) @RequestParam String email,
                                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                                     @ApiParam(value = "회원 닉네임", required = true) @RequestParam String nickName) {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email(email)
                .name(name)
                .build();

        return responseService.getSingleResult(userService.update(userId, userRequestDTO));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원 영구 삭제")
    @DeleteMapping(value = "/user/{userId}")
    public CommonResult delete(@ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult();
    }
}
