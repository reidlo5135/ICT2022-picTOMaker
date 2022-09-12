package kr.co.picTO.qna.dto;

import kr.co.picTO.qna.domain.UserQna;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserQnaResponseDto {
    private long id;
    private String nickname;
    private String qna;

    public UserQnaResponseDto(UserQna userQna) {
        if(userQna.getUser() != null) {
            nickname = userQna.getUser().getNickName();
        } else if(userQna.getSocialUser() != null) {
            nickname = userQna.getSocialUser().getName();
        }
        id = userQna.getId();
        qna = userQna.getQna();
    }
}
