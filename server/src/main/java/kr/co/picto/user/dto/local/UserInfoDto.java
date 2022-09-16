package kr.co.picto.user.dto.local;

import kr.co.picTO.user.domain.local.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String profile_image_url;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .profile_image_url(user.getProfile_image_url())
                .build();
    }
}
