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
    private static final String ClassName = UserQnaController.class.toString();
    private final UserQnaService userQnaService;
    private final ResponseLoggingService loggingService;

    @ApiOperation(value = "문의 등록", notes = "Register QnA")
    @PostMapping(value = "/register/{provider}")
    public ResponseEntity<?> save(@ApiParam(value = "QnaReqDto", required = true) @RequestBody UserQnaRequestDto userQnaRequestDto, @PathVariable String provider) {
        loggingService.httpPathStrLogging(ClassName, "saveQnA", userQnaRequestDto.getEmail(), provider, "");
        return ResponseEntity.ok().body(userQnaService.registerQnA(userQnaRequestDto, provider));
    }
}
