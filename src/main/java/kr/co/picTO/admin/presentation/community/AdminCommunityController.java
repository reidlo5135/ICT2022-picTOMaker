package kr.co.picTO.admin.presentation.community;

import io.swagger.annotations.*;
import kr.co.picTO.admin.application.community.AdminCommunityService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"7. Only Admin - Community"})
@ApiImplicitParams({
        @ApiImplicitParam(
                name = "X-AUTH-TOKEN",
                value = "관리자 AccessToken",
                required = true, dataTypeClass = String.class, paramType = "header")
})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/admin/community")
public class AdminCommunityController {
    private final AdminCommunityService communityService;

    @ApiOperation(value = "전체 게시물 조회", notes = "Select Board All")
    @GetMapping(value = "/find")
    public ResponseEntity<ListResult<UserCommunityResponseDto>> findAll() {
        return ResponseEntity.ok().body(communityService.findBoardAll());
    }

    @ApiOperation(value = "특정 게시물 조회", notes = "Select Board By Id")
    @GetMapping(value = "/find/id/{id}")
    public ResponseEntity<SingleResult<UserCommunityResponseDto>> findById(@ApiParam(value = "board_id", required = true) @PathVariable long id) {
        return ResponseEntity.ok().body(communityService.findBoardById(id));
    }
}
