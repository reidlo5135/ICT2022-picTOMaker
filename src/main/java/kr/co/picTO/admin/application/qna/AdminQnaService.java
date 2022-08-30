package kr.co.picTO.admin.application.qna;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.qna.domain.BaseUserQna;
import kr.co.picTO.qna.domain.BaseUserQnaRepo;
import kr.co.picTO.qna.dto.UserQnaResponseDto;
import kr.co.picTO.qna.exception.CustomQnaNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminQnaService {
    private final BaseUserQnaRepo userQnaRepo;
    private final ResponseService responseService;

    @Transactional(readOnly = true)
    public ListResult<UserQnaResponseDto> findQnaAll() {
        List<UserQnaResponseDto> result = userQnaRepo.findAll().stream().map(UserQnaResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomQnaNotExistException();

        return responseService.getListResult(result);
    }

    @Transactional(readOnly = true)
    public SingleResult<UserQnaResponseDto> findQnaById(long id) {
        BaseUserQna baseUserQna = userQnaRepo.findById(id).orElseThrow(CustomQnaNotExistException::new);

        return responseService.getSingleResult(new UserQnaResponseDto(baseUserQna));
    }
}
