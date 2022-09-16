package kr.co.picto.user.presentation;

import io.swagger.annotations.*;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.user.application.admin.AdminUserService;
import kr.co.picto.user.dto.local.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"6. Only Admin - User"})
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "X-AUTH-TOKEN",
                value = "관리자 AccessToken",
                required = true, dataTypeClass = String.class, paramType = "header")
})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/admin")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @ApiOperation(value = "전체 회원 목록 조회", notes = "모든 회원 조회")
    @GetMapping("/users")
    public ResponseEntity<ListResult<UserResponseDto>> findAllUser() {
        return ResponseEntity.ok().body(adminUserService.findAllUser());
    }

    @ApiOperation(value = "회원 단일 조회", notes = "id로 회원 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<SingleResult<UserResponseDto>> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId, @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return ResponseEntity.ok().body(adminUserService.findById(userId));
    }

    @ApiOperation(value = "회원 단일 조회", notes = "email로 회원 조회")
    @GetMapping("/user/{email}")
    public ResponseEntity<SingleResult<UserResponseDto>> findUserByEmail (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email, @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return ResponseEntity.ok().body(adminUserService.findByEmail(email));
    }

    @ApiOperation(value = "회원 삭제", notes = "id로 회원 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity<SingleResult<Long>> delete(@ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        return ResponseEntity.ok().body(adminUserService.delete(userId));
    }
}