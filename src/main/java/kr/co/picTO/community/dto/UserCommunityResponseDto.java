package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.UserCommunity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunityResponseDto {

    private long id;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserCommunityResponseDto(UserCommunity baseUserCommunity) {
        email = baseUserCommunity.getUser().getEmail();
        nickname = baseUserCommunity.getUser().getNickName();
        id = baseUserCommunity.getId();
        title = baseUserCommunity.getTitle();
        content = baseUserCommunity.getContent();
        createdDate = baseUserCommunity.getCreatedDate();
        modifiedDate = baseUserCommunity.getModifiedDate();
    }
}
