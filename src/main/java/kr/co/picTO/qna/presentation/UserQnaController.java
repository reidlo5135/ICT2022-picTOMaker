package kr.co.picTO.qna.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.application.UserQnaService;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"3. QnA Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/qna")
public class UserQnaController {
    private final UserQnaService userQnaService;

    @ApiOperation(value = "문의 등록", notes = "Register QnA")
    @PostMapping(value = "/register")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "QnaReqDto", required = true) @RequestBody UserQnaRequestDto userQnaRequestDto) {
        return ResponseEntity.ok().body(userQnaService.registerQnA(userQnaRequestDto));
    }
}
