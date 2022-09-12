package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.UserCommunity;
import kr.co.picTO.community.domain.UserCommunityRepository;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
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
