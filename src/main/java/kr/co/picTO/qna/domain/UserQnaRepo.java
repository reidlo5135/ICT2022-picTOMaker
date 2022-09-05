package kr.co.picTO.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserQnaRepo extends JpaRepository<UserQna, Long> {

    Optional<UserQna> findByName(String name);
}
