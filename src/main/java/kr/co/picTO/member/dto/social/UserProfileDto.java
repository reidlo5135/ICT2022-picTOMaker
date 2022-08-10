package kr.co.picTO.member.dto.social;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfileDto {
    private String email;
    private String name;
    private String nickname;
    private String profile_image_url;

    public UserProfileDto(String email, String nickname, String profile_image_url) {
        this.email = email;
        this.nickname = nickname;
        this.profile_image_url = profile_image_url;
    }

    public UserProfileDto(String email, String name, String nickname, String profile_image_url) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profile_image_url = profile_image_url;
    }
}
