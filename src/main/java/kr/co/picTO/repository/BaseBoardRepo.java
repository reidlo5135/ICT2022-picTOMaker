package kr.co.picTO.repository;

import kr.co.picTO.entity.board.BaseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseBoardRepo extends JpaRepository<BaseBoard, Long> {

    Optional<BaseBoard> findByNickName(String nickname);
}
