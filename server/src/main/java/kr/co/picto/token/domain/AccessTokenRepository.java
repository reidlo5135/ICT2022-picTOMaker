package kr.co.picto.token.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    Optional<AccessToken> findById(Long id);

    @Query(value = "SELECT bat FROM AccessToken bat WHERE bat.access_token = :access_token")
    Optional<AccessToken> findByAccessToken(@Param("access_token") String access_token);

    @Modifying
    @Query(value = "DELETE FROM AccessToken bat WHERE bat.access_token = :access_token")
    Integer deleteByAccessToken(@Param("access_token") String access_token);
}
