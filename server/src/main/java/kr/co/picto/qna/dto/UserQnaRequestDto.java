package kr.co.picto.qna.dto;

import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.qna.domain.UserQna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
@NoArgsConstructor
@AllArgsConstructor
public class UserQnaRequestDto {

    private String email;
    private String name;
    private String qna;

    public UserQna toEntity(User user) {
        return UserQna.builder()
                .user(user)
                .name(name)
                .qna(qna)
                .build();
    }

    public UserQna toEntity(SocialUser socialUser) {
        return UserQna.builder()
                .socialUser(socialUser)
                .name(name)
                .qna(qna)
                .build();
    }
}
