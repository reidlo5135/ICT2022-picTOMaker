package kr.co.picto.token.domain.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenId(Long tokenId);

    @Modifying
    @Query(value = "UPDATE RefreshToken rt SET rt.token = :token WHERE rt.tokenId = :id")
    Integer updateById(String token, Long id);

    void deleteByTokenId(Long tokenId);
}
