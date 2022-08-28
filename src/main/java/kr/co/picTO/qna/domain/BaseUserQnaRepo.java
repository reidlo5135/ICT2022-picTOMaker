package kr.co.picTO.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserQnaRepo extends JpaRepository<BaseUserQna, Long> {

    Optional<BaseUserQna> findByName(String name);
}
