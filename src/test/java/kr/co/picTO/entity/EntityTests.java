package kr.co.picTO.entity;

import kr.co.picTO.entity.user.User;
import kr.co.picTO.repository.UserJpaRepo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EntityTests {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        LocalDateTime now = LocalDateTime.of(2021, 8, 5, 22, 31, 0);
        userJpaRepo.save(User.builder()
                .name("준모")
                .email("naru7922@gmail.com")
                .build());

        List<User> users = userJpaRepo.findAll();

        User user = users.get(0);

        System.out.println(">>>>>>> createdDate = " + user.getCreatedDate() + ", modifiedDate = " + user.getModifiedDate());

        Assertions.assertThat(user.getCreatedDate().isAfter(now));
        Assertions.assertThat(user.getModifiedDate().isAfter(now));
    }
}
