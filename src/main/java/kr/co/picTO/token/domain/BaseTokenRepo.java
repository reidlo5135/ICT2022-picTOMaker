package kr.co.picTO.token.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BaseTokenRepo extends JpaRepository<BaseAccessToken, Long> {

    Optional<BaseAccessToken> findById(Long id);

    @Query(value = "SELECT bat FROM BaseAccessToken bat WHERE bat.access_token = :access_token")
    Optional<BaseAccessToken> findByAccessToken(@Param("access_token") String access_token);

    @Modifying
    @Query(value = "DELETE FROM BaseAccessToken bat WHERE bat.access_token = :access_token")
    Integer deleteByAccessToken(@Param("access_token") String access_token);
}
