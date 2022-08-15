package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.BaseUserCommunity;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Getter
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunityRequestDto {

    private long id;
    private String email;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public UserCommunityRequestDto(BaseUserCommunity baseUserCommunity) {
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

    public BaseUserCommunity toBluEntity(BaseLocalUser blu) {
        return BaseUserCommunity.builder()
                .blu(blu)
                .title(title)
                .content(content)
                .build();
    }

    public BaseUserCommunity toBauEntity(BaseAuthUser bau) {
        return BaseUserCommunity.builder()
                .bau(bau)
                .title(title)
                .content(content)
                .build();
    }
}
