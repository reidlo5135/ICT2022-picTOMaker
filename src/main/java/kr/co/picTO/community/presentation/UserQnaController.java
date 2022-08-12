package kr.co.picTO.community.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.community.dto.UserQnaRequestDto;
import kr.co.picTO.community.application.UserQnaService;
import kr.co.picTO.common.application.ResponseLoggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. QnA Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/qna")
public class UserQnaController {
    private static final String className = UserQnaController.class.toString();
    private final UserQnaService userQnaService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "문의 등록", notes = "Register QnA")
    @PostMapping(value = "/register/{provider}")
    public ResponseEntity<?> save(@ApiParam(value = "QnaReqDto", required = true) @RequestBody UserQnaRequestDto userQnaRequestDto, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "saveQnA", userQnaRequestDto.getEmail(), provider, "");
        try {
            ett = userQnaService.registerQnA(userQnaRequestDto, provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Qna Controller register Error Occurred : " + e.getMessage());
        } finally {
            log.info("User Qna Controller register ett : " + ett);
            return ett;
        }
    }
}
