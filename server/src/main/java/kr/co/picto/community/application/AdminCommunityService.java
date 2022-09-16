package kr.co.picto.community.application;

import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.community.domain.UserCommunity;
import kr.co.picto.community.domain.UserCommunityRepository;
import kr.co.picto.community.dto.UserCommunityResponseDto;
import kr.co.picto.community.exception.CustomCommunityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCommunityService {
    private final UserCommunityRepository communityRepository;
    private final ResponseService responseService;


    @Transactional(readOnly = true)
    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<UserCommunityResponseDto> result = communityRepository.findAll().stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomCommunityNotExistException();

        return responseService.getListResult(result);
    }

    @Transactional(readOnly = true)
    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        UserCommunity userCommunity = communityRepository.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        return responseService.getSingleResult(new UserCommunityResponseDto(userCommunity));
    }
}
