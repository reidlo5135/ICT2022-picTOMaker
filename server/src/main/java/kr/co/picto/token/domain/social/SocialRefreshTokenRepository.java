package kr.co.picto.token.domain.social;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SocialRefreshTokenRepository extends JpaRepository<SocialRefreshToken, Long> {

    Optional<SocialRefreshToken> findByTokenId(Long tokenId);

    @Query(value = "SELECT srt.tokenId from SocialRefreshToken srt WHERE srt.token = :token")
    Long findIdByToken(String token);

    @Modifying
    @Query(value = "UPDATE SocialRefreshToken srt SET srt.token = :token WHERE srt.tokenId = :id")
    Integer updateById(String token, Long id);

    void deleteByTokenId(Long tokenId);
}
