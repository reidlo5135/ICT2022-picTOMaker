package kr.co.picTO.domain.user;

import kr.co.picTO.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);
}
