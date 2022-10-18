package kr.co.picto.skeleton.domain;

import kr.co.picto.skeleton.TypeEnum;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.local.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SkeletonRepository extends JpaRepository<Skeleton, Long> {

    Optional<Skeleton> findByUser(User user);
    Optional<Skeleton> findBySocialUser(SocialUser socialUser);

    @Modifying
    @Query(value = "UPDATE Skeleton sk SET sk.coordinate = :coordinate, sk.type = :type WHERE sk.user = :user")
    void update(String coordinate, User user, TypeEnum type);

    @Modifying
    @Query(value = "UPDATE Skeleton sk SET sk.coordinate = :coordinate, sk.type = :type WHERE sk.socialUser = :socialUser")
    void update(String coordinate, SocialUser socialUser, TypeEnum type);

    @Modifying
    @Query(value = "DELETE FROM Skeleton sk WHERE sk.user = :user")
    Integer deleteByUserId(User user);
}
