package kr.co.picTO.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "mockUser")
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    private static final String accessToken = "";

    @Test
    public void 액세스토큰으로_사용자정보_요청() throws Exception {
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);

        assertThat(kakaoProfile).isNotNull();
        assertThat(kakaoProfile.getKakao_account().getEmail()).isEqualTo("naru5135@naver.com");
        assertThat(kakaoProfile.getProperties().getNickname()).isEqualTo("강준모");
    }
}
