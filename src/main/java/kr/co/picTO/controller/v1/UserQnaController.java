package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.dto.qna.UserQnaRequestDto;
import kr.co.picTO.service.qna.UserQnaService;
import kr.co.picTO.service.response.ResponseLoggingService;
import kr.co.picTO.service.response.ResponseService;
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
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "문의 등록", notes = "Register QnA")
    @PostMapping(value = "/register/{provider}")
    public ResponseEntity<?> save(@ApiParam(value = "QnaReqDto", required = true) @RequestBody UserQnaRequestDto userQnaRequestDto, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        log.info("User Qna Controller register userQnaRequestDto.getEmail() : " + userQnaRequestDto.getEmail());
        log.info("User Qna Controller register provider : " + provider);

        try {
            ett = userQnaService.registerQnA(userQnaRequestDto, provider);
            log.info("User Qna Controller register ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User Qna Controller register Error Occurred : " + e.getMessage());
        }

        return ett;
    }
}
