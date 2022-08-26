package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.BaseUserQna;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.social.BaseAuthUser;
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

    public BaseUserQna toEntity(BaseLocalUser blu) {
        return BaseUserQna.builder()
                .blu(blu)
                .name(name)
                .qna(qna)
                .build();
    }

    public BaseUserQna toEntity(BaseAuthUser bau) {
        return BaseUserQna.builder()
                .bau(bau)
                .name(name)
                .qna(qna)
                .build();
    }
}
