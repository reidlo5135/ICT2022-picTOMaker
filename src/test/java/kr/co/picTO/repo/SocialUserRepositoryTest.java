package kr.co.picTO.repo;

import kr.co.picTO.user.domain.social.AccountRole;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.user.domain.social.SocialUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocialUserRepositoryTest {

    private static final String TEST_EMAIL = "naru5135@naver.com";
    private static final String TEST_NAME = "강준모";
    private static final String TEST_PIC = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(SocialUserRepositoryTest.class);

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    SocialUserRepository authUserRepo;

    @Test
    void findByEmail() {
        SocialUser bau = SocialUser.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .picture(TEST_PIC)
                .role(AccountRole.KAKAO)
                .provider(AccountRole.KAKAO.getKey())
                .build();

        testEntityManager.persist(bau);

        assertEquals("BAU findByEmail : ", bau, authUserRepo.findByEmail(TEST_EMAIL).get());
        LOGGER.info("BAU Log : " + bau);
    }
}
