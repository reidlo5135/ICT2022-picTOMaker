package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.UserCommunity;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.social.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class UserCommunityRequestDto {

    private String email;
    private String title;
    private String content;

    public UserCommunity toEntity(User user) {
        return UserCommunity.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

    public UserCommunity toEntity(SocialUser socialUser) {
        return UserCommunity.builder()
                .socialUser(socialUser)
                .title(title)
                .content(content)
                .build();
    }
}
