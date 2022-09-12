package kr.co.picTO.community.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCommunityRepository extends JpaRepository<UserCommunity, Long> {

    Optional<UserCommunity> findById(long id);
}
