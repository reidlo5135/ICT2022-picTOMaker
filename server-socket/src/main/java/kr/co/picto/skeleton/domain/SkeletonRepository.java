package kr.co.picto.skeleton.domain;

import kr.co.picto.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SkeletonRepository extends JpaRepository<Skeleton, Long> {

    Optional<Skeleton> findByUser(User user);

    @Modifying
    @Query(value = "DELETE FROM Skeleton sk WHERE sk.user = :user")
    Integer deleteByUserId(User user);
}
