package kr.co.picto.qna.application;

import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.qna.domain.UserQna;
import kr.co.picto.qna.domain.UserQnaRepository;
import kr.co.picto.qna.dto.UserQnaResponseDto;
import kr.co.picto.qna.exception.CustomQnaNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Service
@RequiredArgsConstructor
public class AdminQnaService {
    private final UserQnaRepository userQnaRepository;
    private final ResponseService responseService;

    @Transactional(readOnly = true)
    public ListResult<UserQnaResponseDto> findQnaAll() {
        List<UserQnaResponseDto> result = userQnaRepository.findAll().stream().map(UserQnaResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomQnaNotExistException();

        return responseService.getListResult(result);
    }

    @Transactional(readOnly = true)
    public SingleResult<UserQnaResponseDto> findQnaById(long id) {
        UserQna userQna = userQnaRepository.findById(id).orElseThrow(CustomQnaNotExistException::new);

        return responseService.getSingleResult(new UserQnaResponseDto(userQna));
    }
}
