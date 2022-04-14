package kr.co.picTO.dto.social;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String email;
    private String nickname;
    private String profile_image_url;

    public ProfileDTO(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
