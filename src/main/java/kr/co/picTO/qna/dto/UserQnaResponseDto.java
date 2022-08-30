package kr.co.picTO.qna.dto;

import kr.co.picTO.qna.domain.BaseUserQna;
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

    public UserQnaResponseDto(BaseUserQna baseUserQna) {
        if(baseUserQna.getBlu() != null) {
            nickname = baseUserQna.getBlu().getNickName();
        } else if(baseUserQna.getBau() != null) {
            nickname = baseUserQna.getBau().getName();
        }
        id = baseUserQna.getId();
        qna = baseUserQna.getQna();
    }
}
