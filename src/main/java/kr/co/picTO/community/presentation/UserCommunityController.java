package kr.co.picTO.community.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.community.application.UserCommunityService;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"6. Community Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/community")
public class UserCommunityController {
    private static final String className = UserCommunityController.class.toString();
    private final UserCommunityService userCommunityService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "전체 게시물 조회", notes = "Select Board All")
    @GetMapping(value = "/find/all")
    public ResponseEntity<?> selectAll() {
        ResponseEntity<?> ett = null;
        try {
            ett = userCommunityService.findBoardAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Community Controller selectAll Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Community Controller selectAll ett : " + ett);
            return ett;
        }
    }

    @ApiOperation(value = "특정 게시물 조회", notes = "Select Board By Id")
    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> selectById(@ApiParam(value = "board_id", required = true) @PathVariable long id) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "selectById", String.valueOf(id), "", "");
        try {
            ett = userCommunityService.findBoardById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Community Controller selectById Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Community Controller selectById ett : " + ett);
            return ett;
        }
    }

    @ApiOperation(value = "게시물 등록", notes = "Register Board")
    @PostMapping(value = "/register/{provider}")
    public ResponseEntity<?> save(@ApiParam(value = "CommunityReqDto", required = true) @RequestBody UserCommunityRequestDto userCommunityRequestDto, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "saveBoard", userCommunityRequestDto.getEmail(), provider, "");
        try {
            ett = userCommunityService.registerBoard(userCommunityRequestDto, provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Community Controller register Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Community Controller register ett : " + ett);
            return ett;
        }
    }
}
