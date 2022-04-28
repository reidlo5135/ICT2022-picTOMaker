package kr.co.picTO.repository;

import kr.co.picTO.entity.local.BaseLocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseLocalUserRepo extends JpaRepository<BaseLocalUser, Long> {

}
