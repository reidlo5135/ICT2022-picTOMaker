package kr.co.picTO.user.dto;

import kr.co.picTO.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateDto {
    private String nickName;
    private String introduction;
    private String profile_image_url;

    @Builder
    public UserUpdateDto(String nickName, String introduction, String profile_image_url) {
        this.nickName = nickName;
        this.introduction = introduction;
        this.profile_image_url = profile_image_url;
    }

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .introduction(introduction)
                .profile_image_url(profile_image_url)
                .build();
    }
}
