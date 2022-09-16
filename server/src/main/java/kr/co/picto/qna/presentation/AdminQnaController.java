package kr.co.picto.qna.presentation;

import io.swagger.annotations.*;
import kr.co.picTO.qna.application.AdminQnaService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.dto.UserQnaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"8. Only Admin - QnA"})
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "X-AUTH-TOKEN",
                value = "관리자 AccessToken",
                required = true, dataTypeClass = String.class, paramType = "header")
})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/admin/qna")
public class AdminQnaController {
    private final AdminQnaService qnaService;

    @GetMapping
    @ApiOperation(value = "전체 문의사항 조회", notes = "Select Qna All")
    public ResponseEntity<ListResult<UserQnaResponseDto>> findAll() {
        return ResponseEntity.ok().body(qnaService.findQnaAll());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Id로 특정 문의사항 조회", notes = "Select Qna By Id")
    public ResponseEntity<SingleResult<UserQnaResponseDto>> findById(@ApiParam(value = "qna_id", required = true) @PathVariable long id) {
        return ResponseEntity.ok().body(qnaService.findQnaById(id));
    }
}
