package kr.co.picTO.user.dto.social;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserInfoDto {
    private String email;
    private String name;
    private String profile_image_url;
}
