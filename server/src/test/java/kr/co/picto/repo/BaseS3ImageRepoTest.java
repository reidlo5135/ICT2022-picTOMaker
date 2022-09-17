package kr.co.picto.repo;

import kr.co.picto.file.domain.S3ImageRepository;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseS3ImageRepoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseS3ImageRepoTest.class);

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    S3ImageRepository s3ImageRepository;
}
