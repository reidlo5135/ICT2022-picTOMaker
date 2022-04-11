package kr.co.picTO.repository;

import kr.co.picTO.entity.oauth.BaseAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseAuthUserRepo extends JpaRepository<BaseAuthUser, Long> {

    Optional<BaseAuthUser> findByEmail(String email);
}
