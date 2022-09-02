package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.BaseUserCommunity;
import kr.co.picTO.community.domain.BaseUserCommunityRepo;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.domain.social.BaseAuthUser;
import kr.co.picTO.user.domain.social.BaseAuthUserRepo;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCommunityService {
    private final BaseUserCommunityRepo communityRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;

    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<UserCommunityResponseDto> result = communityRepo.findAll().stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomCommunityNotExistException();

        return responseService.getListResult(result);
    }

    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        BaseUserCommunity baseUserCommunity = communityRepo.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        return responseService.getSingleResult(new UserCommunityResponseDto(baseUserCommunity));
    }

    public SingleResult<Long> registerBoard(UserCommunityRequestDto userCommunityRequestDto, String provider) {
        long result;
        if(provider != null && provider.equals("LOCAL")) {
            BaseLocalUser blu = userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepo.save(userCommunityRequestDto.toEntity(blu)).getId();
        } else {
            BaseAuthUser bau = authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepo.save(userCommunityRequestDto.toEntity(bau)).getId();
        }

        return responseService.getSingleResult(result);
    }
}
