package kr.co.picTO.file.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface S3ImageRepository extends JpaRepository<S3Image, Long> {

    Optional<S3Image> findByEmail(String email);

    List<S3Image> findByEmailAndProvider(String email, String provider);

    Long countByEmailAndProvider(String email, String provider);

    @Modifying
    @Query(value = "UPDATE S3Image bsi SET bsi.fileUrl = :fileUrl WHERE bsi.email = :email AND bsi.id = :id")
    Integer updateByEmailAndId(String fileUrl, String email, Long id);

    @Modifying
    @Query(value = "DELETE FROM S3Image bsi WHERE bsi.id = :id")
    Integer deleteByFileId(Long id);
}
