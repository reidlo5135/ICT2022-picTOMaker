package kr.co.picTO.repository;

import kr.co.picTO.entity.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByKey(Long key);
}
