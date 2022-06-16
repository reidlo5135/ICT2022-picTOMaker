package kr.co.picTO.repository;

import kr.co.picTO.entity.local.BaseLocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseLocalUserRepo extends JpaRepository<BaseLocalUser, Long> {
    Optional<BaseLocalUser> findByEmail(String email);
    Optional<BaseLocalUser> findByEmailAndProvider(String email, String provider);
}
