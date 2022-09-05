package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.UserCommunity;
import kr.co.picTO.community.domain.UserCommunityRepo;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
import kr.co.picTO.user.domain.User;
import kr.co.picTO.user.domain.UserRepository;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommunityService {
    private final UserCommunityRepo communityRepository;
    private final UserRepository userRepository;
    private final ResponseService responseService;

    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<UserCommunityResponseDto> result = communityRepository.findAll().stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomCommunityNotExistException();

        return responseService.getListResult(result);
    }

    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        UserCommunity baseUserCommunity = communityRepository.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        return responseService.getSingleResult(new UserCommunityResponseDto(baseUserCommunity));
    }

    public SingleResult<Long> registerBoard(UserCommunityRequestDto userCommunityRequestDto) {
        User user = userRepository.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);

        return responseService.getSingleResult(communityRepository.save(userCommunityRequestDto.toEntity(user)).getId());
    }
}
