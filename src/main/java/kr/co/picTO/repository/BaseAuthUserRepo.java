package kr.co.picTO.repository;

import kr.co.picTO.entity.oauth2.BaseAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseAuthUserRepo extends JpaRepository<BaseAuthUser, Long> {

    Optional<BaseAuthUser> findByEmail(String email);
    Optional<BaseAuthUser> findByEmailAndProvider(String email, String provider);
}
