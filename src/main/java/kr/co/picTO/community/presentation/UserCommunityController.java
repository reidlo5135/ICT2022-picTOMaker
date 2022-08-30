package kr.co.picTO.community.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.application.UserCommunityService;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. Community Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/community")
public class UserCommunityController {
    private final UserCommunityService userCommunityService;

    @ApiOperation(value = "전체 게시물 조회", notes = "Select Board All")
    @GetMapping(value = "/find")
    public ResponseEntity<ListResult<UserCommunityResponseDto>> findAll() {
        return ResponseEntity.ok().body(userCommunityService.findBoardAll());
    }

    @ApiOperation(value = "특정 게시물 조회", notes = "Select Board By Id")
    @GetMapping(value = "/find/id/{id}")
    public ResponseEntity<SingleResult<UserCommunityResponseDto>> findById(@ApiParam(value = "board_id", required = true) @PathVariable long id) {
        return ResponseEntity.ok().body(userCommunityService.findBoardById(id));
    }

    @ApiOperation(value = "게시물 등록", notes = "Register Board")
    @PostMapping(value = "/register/provider/{provider}")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "CommunityReqDto", required = true) @RequestBody UserCommunityRequestDto userCommunityRequestDto, @PathVariable String provider) {
        return ResponseEntity.ok().body(userCommunityService.registerBoard(userCommunityRequestDto, provider));
    }
}
