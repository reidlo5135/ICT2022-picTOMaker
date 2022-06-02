package kr.co.picTO.repo;

import kr.co.picTO.repository.BaseTokenRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class BaseTokenRepoTest {

    @Autowired
    BaseTokenRepo tokenRepo;

    @Test
    void findByAccessToken() {

    }
}
