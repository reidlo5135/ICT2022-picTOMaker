package kr.co.picTO.member.domain;

import kr.co.picTO.member.domain.local.BaseLocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BaseLocalUserRepo extends JpaRepository<BaseLocalUser, Long> {

    Optional<BaseLocalUser> findByEmail(String email);
    Optional<BaseLocalUser> findByEmailAndProvider(String email, String provider);

    @Modifying
    @Query(value = "DELETE FROM BaseAccessToken bat WHERE bat.access_token = :access_token")
    Integer deleteByAccessToken(@Param("access_token") String access_token);
}
