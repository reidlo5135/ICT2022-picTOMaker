package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.UserCommunity;
import kr.co.picTO.community.domain.UserCommunityRepository;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.local.UserRepository;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.user.domain.social.SocialUserRepository;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Service
@RequiredArgsConstructor
public class UserCommunityService {
    private final UserCommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final ResponseService responseService;

    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<UserCommunityResponseDto> result = communityRepository.findAll().stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        if(result.isEmpty()) throw new CustomCommunityNotExistException();

        return responseService.getListResult(result);
    }

    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        UserCommunity userCommunity = communityRepository.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        return responseService.getSingleResult(new UserCommunityResponseDto(userCommunity));
    }

    public SingleResult<Long> registerBoard(UserCommunityRequestDto userCommunityRequestDto, String provider) {
        long result;
        if(provider != null && provider.equals("LOCAL")) {
            User blu = userRepository.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepository.save(userCommunityRequestDto.toEntity(blu)).getId();
        } else {
            SocialUser bau = socialUserRepository.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepository.save(userCommunityRequestDto.toEntity(bau)).getId();
        }

        return responseService.getSingleResult(result);
    }
}
