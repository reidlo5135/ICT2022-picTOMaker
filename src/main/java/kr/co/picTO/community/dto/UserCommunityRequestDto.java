package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.BaseUserCommunity;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAuthUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCommunityRequestDto {

    private String email;
    private String title;
    private String content;

    public BaseUserCommunity toEntity(BaseLocalUser blu, BaseAuthUser bau, String provider) {
        log.info("User CommunityDto blu : " + blu.getEmail());
        log.info("User CommunityDto bau : " + bau.getEmail());
        if(provider.equals("LOCAL")) {
            return BaseUserCommunity.builder()
                    .blu(blu)
                    .email(email)
                    .title(title)
                    .content(content)
                    .provider(provider)
                    .build();
        } else {
            return BaseUserCommunity.builder()
                    .bau(bau)
                    .email(email)
                    .title(title)
                    .content(content)
                    .provider(provider)
                    .build();
        }
    }
}
