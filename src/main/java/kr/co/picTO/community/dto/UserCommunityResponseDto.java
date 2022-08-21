package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.BaseUserCommunity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Getter
@Log4j2
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

    public UserCommunityResponseDto(BaseUserCommunity baseUserCommunity) {
        if(baseUserCommunity.getBlu() != null) {
            email = baseUserCommunity.getBlu().getEmail();
            nickname = baseUserCommunity.getBlu().getNickName();
        } else if(baseUserCommunity.getBau() != null) {
            email = baseUserCommunity.getBau().getEmail();
            nickname = baseUserCommunity.getBau().getName();
        }
        id = baseUserCommunity.getId();
        title = baseUserCommunity.getTitle();
        content = baseUserCommunity.getContent();
        createdDate = baseUserCommunity.getCreatedDate();
        modifiedDate = baseUserCommunity.getModifiedDate();
    }
}
