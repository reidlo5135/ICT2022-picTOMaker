package kr.co.picTO.member.presentation;

import io.swagger.annotations.*;
import kr.co.picTO.member.application.AdminUserService;
import kr.co.picTO.common.application.ResponseLoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. Only Admin - User"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/admin")
public class AdminController {

    private static final String ClassName = AdminController.class.toString();

    private final AdminUserService adminUserService;
    private final ResponseLoggingService loggingService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색", notes = "userId로 회원을 조회합니다.")
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<?> findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable Long userId, @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        loggingService.httpPathStrLogging(ClassName, "findUserById", String.valueOf(userId), "", "");
        return ResponseEntity.ok().body(adminUserService.findById(userId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 단건 검색 (이메일)", notes = "이메일로 회원을 조회합니다.")
    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> findUserByEmail (@ApiParam(value = "회원 이메일", required = true) @PathVariable String email, @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        loggingService.httpPathStrLogging(ClassName, "findUserByEmail", email, "", "");
        return ResponseEntity.ok().body(adminUserService.findByEmail(email));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 목록 조회", notes = "모든 회원을 조회합니다.")
    @GetMapping("/users")
    public ResponseEntity<?> findAllUser() {
        return ResponseEntity.ok().body(adminUserService.findAllUser());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제합니다.")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> delete(@ApiParam(value = "회원 아이디", required = true) @PathVariable Long userId) {
        loggingService.httpPathStrLogging(ClassName, "delete", String.valueOf(userId), "", "");
        return ResponseEntity.ok().body(adminUserService.delete(userId));
    }
}