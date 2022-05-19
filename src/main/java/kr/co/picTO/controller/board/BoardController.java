package kr.co.picTO.controller.board;

import io.swagger.annotations.Api;
import kr.co.picTO.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. OAuth2 User"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;
}
