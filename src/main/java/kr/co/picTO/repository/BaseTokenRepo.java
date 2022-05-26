package kr.co.picTO.repository;

import kr.co.picTO.entity.oauth2.BaseAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseTokenRepo extends JpaRepository<BaseAccessToken, String> {

    Optional<BaseAccessToken> findByKey(Long id);

    Long update(BaseAccessToken bat);
}
