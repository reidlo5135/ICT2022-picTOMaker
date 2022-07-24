package kr.co.picTO.repository;

import kr.co.picTO.entity.s3.BaseS3Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BaseS3ImageRepo extends JpaRepository<BaseS3Image, Long> {

    Optional<BaseS3Image> findByEmail(String email);

    List<BaseS3Image> findAllByEmail(String email);

    Long countByEmailAndProvider(String email, String provider);

    @Modifying
    @Query(value = "DELETE FROM BaseS3Image bsi WHERE bsi.fileName = :name")
    Integer deleteByFileName(String name);
}
