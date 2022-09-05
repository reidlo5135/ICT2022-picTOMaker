package kr.co.picTO.qna.dto;

import kr.co.picTO.qna.domain.UserQna;
import kr.co.picTO.user.domain.User;
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
}
