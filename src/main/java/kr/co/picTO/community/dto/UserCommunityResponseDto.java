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

    public UserCommunityResponseDto(UserCommunity userCommunity) {
        if(userCommunity.getUser() != null) {
            email = userCommunity.getUser().getEmail();
            nickname = userCommunity.getUser().getNickName();
        } else if(userCommunity.getSocialUser() != null) {
            email = userCommunity.getSocialUser().getEmail();
            nickname = userCommunity.getSocialUser().getName();
        }
        id = userCommunity.getId();
        title = userCommunity.getTitle();
        content = userCommunity.getContent();
        createdDate = userCommunity.getCreatedDate();
        modifiedDate = userCommunity.getModifiedDate();
    }
}
