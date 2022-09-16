package kr.co.picto.qna.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.application.UserQnaService;
import kr.co.picTO.qna.dto.UserQnaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"3. QnA Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/qna")
public class UserQnaController {
    private final UserQnaService userQnaService;

    /**
     * frontend - QnA.js
     **/
    @PostMapping(value = "/{provider}")
    @ApiOperation(value = "문의 등록", notes = "Register QnA")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "QnaReqDto", required = true) @RequestBody UserQnaRequestDto userQnaRequestDto, @PathVariable String provider) {
        return ResponseEntity.ok().body(userQnaService.registerQnA(userQnaRequestDto, provider));
    }
}
