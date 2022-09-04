package kr.co.picTO.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.picTO.auth.advice.LoggedInUser;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.user.application.UserService;
import kr.co.picTO.user.dto.UserCreateDto;
import kr.co.picTO.user.dto.UserInfoDto;
import kr.co.picTO.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"9. User Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "User SignUp")
    @PostMapping("/signup")
    public ResponseEntity<SingleResult<UserInfoDto>> create(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok().body(userService.save(userCreateDto));
    }

    @ApiOperation(value = "token으로 로그인 한 회원 정보 조회", notes = "LoggedInUser Info")
    @GetMapping("/myinfo")
    public ResponseEntity<SingleResult<UserInfoDto>> read(@LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userInfoDto.getId()));
    }

    @ApiOperation(value = "user_id로 회원 정보 조회", notes = "Read UserInfo By user_id")
    @GetMapping("/{userId}")
    public ResponseEntity<SingleResult<UserInfoDto>> read(@PathVariable long userId) {
        return ResponseEntity.ok().body(userService.findInfoDtoById(userId));
    }

//    @ApiOperation(value = "token으로 로그인 한 회원 정보 조회", notes = "LoggedInUser Info")
//    @GetMapping("/loggedIn")
//    public ResponseEntity<UserInfoDto> loggedIn(@LoggedInUser UserInfoDto userInfoDto) {
//        return ResponseEntity.ok().body(userInfoDto);
//    }

    @ApiOperation(value = "회원 정보 수정", notes = "Update User Info")
    @PutMapping
    public ResponseEntity<SingleResult<UserInfoDto>> update(@RequestBody UserUpdateDto userUpdateDto, @LoggedInUser UserInfoDto userInfoDto) {
        return ResponseEntity.ok().body(userService.update(userUpdateDto, userInfoDto.getId()));
    }

    @ApiOperation(value = "회원 정보 삭제", notes = "Delete User Info")
    @DeleteMapping
    public ResponseEntity delete(@LoggedInUser UserInfoDto userInfoDto) {
        userService.delete(userInfoDto.getId());

        return ResponseEntity.ok().build();
    }
}
