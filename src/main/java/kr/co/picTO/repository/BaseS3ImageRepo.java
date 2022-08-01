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
    @Query(value = "UPDATE BaseS3Image bsi SET bsi.fileUrl = :fileUrl WHERE bsi.email = :email AND bsi.id = :id")
    Integer updateByEmailAndId(String fileUrl, String email, Long id);

    @Modifying
    @Query(value = "DELETE FROM BaseS3Image bsi WHERE bsi.id = :id")
    Integer deleteByFileName(Long id);
}
