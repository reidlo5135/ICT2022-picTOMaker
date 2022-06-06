package kr.co.picTO.repository;

import kr.co.picTO.entity.s3.BaseS3Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseS3ImageRepo extends JpaRepository<BaseS3Image, Long> {

    Optional<BaseS3Image> findByEmail(String email);
}
