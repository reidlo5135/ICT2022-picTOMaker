package kr.co.picto.user.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    Optional<SocialUser> findByEmailAndProvider(String email, String provider);
}
