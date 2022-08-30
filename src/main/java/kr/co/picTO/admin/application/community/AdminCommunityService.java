package kr.co.picTO.admin.application.community;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.BaseUserCommunity;
import kr.co.picTO.community.domain.BaseUserCommunityRepo;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.domain.social.BaseAuthUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCommunityService {
    private final BaseUserCommunityRepo communityRepo;
    private final BaseLocalUserRepo localUserRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;


    @Transactional(readOnly = true)
    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<UserCommunityResponseDto> result = communityRepo.findAll().stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomCommunityNotExistException();

        return responseService.getListResult(result);
    }

    @Transactional(readOnly = true)
    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        BaseUserCommunity baseUserCommunity = communityRepo.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        return responseService.getSingleResult(new UserCommunityResponseDto(baseUserCommunity));
    }
}
