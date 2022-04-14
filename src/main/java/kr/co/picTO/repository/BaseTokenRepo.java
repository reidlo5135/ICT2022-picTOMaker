package kr.co.picTO.repository;

import kr.co.picTO.entity.oauth.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseTokenRepo extends JpaRepository<AccessToken, String> {
}
