package kr.co.picTO.repository;

import kr.co.picTO.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    User findByName(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, String provider);
}
