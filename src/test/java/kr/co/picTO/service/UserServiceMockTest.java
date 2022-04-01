package kr.co.picTO.service;

import kr.co.picTO.entity.user.User;
import kr.co.picTO.service.user.RestUserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RunWith(SpringRunner.class)
@RestClientTest(RestUserService.class)
public class UserServiceMockTest {

    @Autowired
    private RestUserService restUserService;

    @Autowired
    private MockRestServiceServer serviceServer;

    @Test
    public void 회원가져오기() throws Exception {
        serviceServer
                .expect(MockRestRequestMatchers.requestTo("/picTO/v1/user/email/naru7922@gmail.com"))
                .andRespond(MockRestResponseCreators.withSuccess(
                        new ClassPathResource("/test.json", getClass()),
                        MediaType.APPLICATION_JSON
                ));

        User byEmail = restUserService.getUserByEmail("naru7922@gmail.com");

        Assertions.assertThat(byEmail.getEmail()).isEqualTo("naru7922@gmail.com");
        Assertions.assertThat(byEmail.getName()).isEqualTo("reidlo");
        serviceServer.verify();
    }
}
