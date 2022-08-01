package kr.co.picTO.controller.v1;

import io.swagger.annotations.*;
import kr.co.picTO.service.admin.AdminUserService;
import kr.co.picTO.service.response.ResponseLoggingService;
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

    private static final String className = AdminController.class.toString();

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
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "findUserById", String.valueOf(userId), "", "");
        try {
            ett = adminUserService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("AdminController findUserById ett : " + ett);
            return ett;
        }
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
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "findUserByEmail", email, "", "");
        try {
            ett = adminUserService.findByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("AdminController findUserByEmail ett : " + ett);
            return ett;
        }
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
        ResponseEntity<?> ett = null;
        try {
            ett = adminUserService.findAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("AdminController findAllUser ett : " + ett);
            return ett;
        }
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
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "delete", String.valueOf(userId), "", "");
        try {
            ett = adminUserService.delete(userId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("AdminController findUserByEmail ett : " + ett);
            return ett;
        }
    }
}
