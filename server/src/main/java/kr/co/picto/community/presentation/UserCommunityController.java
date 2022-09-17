package kr.co.picto.community.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.community.application.UserCommunityService;
import kr.co.picto.community.dto.UserCommunityRequestDto;
import kr.co.picto.community.dto.UserCommunityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"4. Community Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/community")
public class UserCommunityController {
    private final UserCommunityService userCommunityService;

    /**
     * frontend - Community.js
    **/
    @GetMapping(value = "/")
    @ApiOperation(value = "전체 게시물 조회", notes = "Select Board All")
    public ResponseEntity<ListResult<UserCommunityResponseDto>> findAll() {
        return ResponseEntity.ok().body(userCommunityService.findBoardAll());
    }

    /**
     * frontend - CommunitytDetails.js
     **/
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "특정 게시물 조회", notes = "Select Board By Id")
    public ResponseEntity<SingleResult<UserCommunityResponseDto>> findById(@ApiParam(value = "board_id", required = true) @PathVariable long id) {
        return ResponseEntity.ok().body(userCommunityService.findBoardById(id));
    }

    /**
     * frontend - CommunityPosting.js
     **/
    @PostMapping(value = "/{provider}")
    @ApiOperation(value = "게시물 등록", notes = "Register Board")
    public ResponseEntity<SingleResult<Long>> save(@ApiParam(value = "CommunityReqDto", required = true) @RequestBody UserCommunityRequestDto userCommunityRequestDto, @PathVariable String provider) {
        return ResponseEntity.ok().body(userCommunityService.registerBoard(userCommunityRequestDto, provider));
    }
}
