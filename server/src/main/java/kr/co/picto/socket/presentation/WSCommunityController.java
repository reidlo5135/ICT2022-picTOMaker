package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.community.application.UserCommunityService;
import kr.co.picto.community.dto.UserCommunityRequestDto;
import kr.co.picto.community.dto.UserCommunityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/community/")
public class WSCommunityController {
    private final UserCommunityService userCommunityService;

    /**
     * frontend - Community.js
     **/
    @MessageMapping
    @SendTo(value = "/sub/community")
    public ResponseEntity<ListResult<UserCommunityResponseDto>> findAll() {
        return ResponseEntity.ok().body(userCommunityService.findBoardAll());
    }

    /**
     * frontend - CommunityDetails.js
     **/
    @SendTo(value = "/sub/community/find")
    @MessageMapping(value = "/{id}")
    public ResponseEntity<SingleResult<UserCommunityResponseDto>> findById(@DestinationVariable(value = "id") long id) {
        return ResponseEntity.ok().body(userCommunityService.findBoardById(id));
    }

    /**
     * frontend - CommunityPosting.js
     **/
    @SendTo(value = "/sub/community/register")
    @MessageMapping(value = "/register/{provider}")
    public ResponseEntity<SingleResult<Long>> save(UserCommunityRequestDto userCommunityRequestDto, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(userCommunityService.registerBoard(userCommunityRequestDto, provider));
    }
}
