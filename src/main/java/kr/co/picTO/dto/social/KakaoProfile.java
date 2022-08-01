package kr.co.picTO.dto.social;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakaoProfile {
    KakaoAccount kakao_account;
    Properties properties;

    @Data
    public class KakaoAccount {
        private String email;
    }

    @Data
    public class Properties {
        private String nickname;
        private String profile_image;
    }
}
