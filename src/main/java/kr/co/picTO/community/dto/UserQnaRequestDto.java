package kr.co.picTO.community.dto;

import kr.co.picTO.community.domain.BaseUserQna;
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

    public BaseUserQna toEntity() {
        return BaseUserQna.builder()
                .email(email)
                .name(name)
                .qna(qna)
                .build();
    }
}
