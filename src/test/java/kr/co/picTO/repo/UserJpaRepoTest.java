package kr.co.picTO.repo;

import kr.co.picTO.advice.exception.CUserNotFoundException;
import kr.co.picTO.entity.user.User;
import kr.co.picTO.repository.UserJpaRepo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJpaRepoTest {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String name = "reidlo";
    private String email = "naru7922@gmail.com";
    private String password = "12";

    @Test
    public void 회원저장_후_이메일로_회원검색() throws Exception {
        userJpaRepo.save(User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        assertNotNull(user);
        assertEquals(user.getUsername(), user.getUsername());
        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getNickName()).isEqualTo(name);
    }
}
