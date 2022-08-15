package kr.co.picTO.community.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserCommunityRepo extends JpaRepository<BaseUserCommunity, Long> {

    Optional<BaseUserCommunity> findById(long id);
}
