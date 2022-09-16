package kr.co.picto.user.dto.local;

import kr.co.picto.user.domain.local.User;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String nickName;
    private String profile_image_url;

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .profile_image_url(profile_image_url)
                .build();
    }
}
