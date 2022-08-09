package kr.co.picTO.repository;

import kr.co.picTO.entity.community.BaseCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseCommunityRepo extends JpaRepository<BaseCommunity, Long> {
}
