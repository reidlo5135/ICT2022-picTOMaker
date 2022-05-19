package kr.co.picTO.service.board;

import kr.co.picTO.repository.BaseBoardRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BaseBoardRepo baseBoardRepo;
}
