package kr.co.picTO.repository;

import kr.co.picTO.entity.oauth2.BaseAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseTokenRepo extends JpaRepository<BaseAccessToken, String> {
}
