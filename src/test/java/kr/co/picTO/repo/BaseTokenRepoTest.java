package kr.co.picTO.repo;

import kr.co.picTO.entity.oauth2.BaseAccessToken;
import kr.co.picTO.repository.BaseTokenRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseTokenRepoTest {

    private static final String TEST_BAT = "asdfasdfasdfasdf";
    private static final String TEST_RET = "qwerqwerqwerqwer";
    private static final Long accessTokenValidMillisecond = 60 * 60 * 1000L;
    private static final Long refreshTokenValidMillisecond = 14 * 24 * 60 * 60 * 1000L;
    private static final Date now = new Date();
    private static final Date expire_access = new Date(now.getTime() + accessTokenValidMillisecond);
    private static final Date expire_refresh = new Date(now.getTime() + refreshTokenValidMillisecond);

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTokenRepoTest.class);

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BaseTokenRepo tokenRepo;

    @Test
    void findByAccessToken() {
        BaseAccessToken bat = BaseAccessToken.builder()
                .token_type("bearer")
                .access_token(TEST_BAT)
                .expires_in(expire_access.getTime())
                .refresh_token(TEST_RET)
                .refresh_token_expires_in(expire_refresh.getTime())
                .provider("LOCAL")
                .build();

        testEntityManager.persist(bat);

        assertEquals(bat, testEntityManager.find(BaseAccessToken.class, bat.getId()));
        assertEquals("BAT equals", bat.getAccess_token(), TEST_BAT);
        assertEquals("BAT tokenRepo : ", bat, tokenRepo.findByAccessToken(TEST_BAT));
        LOGGER.info("BAT : " + bat);
    }
}
