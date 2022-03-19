package kr.co.picTO.repo;

import kr.co.picTO.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);
}
