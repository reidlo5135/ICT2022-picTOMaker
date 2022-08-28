package kr.co.picTO.community.application;

import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.community.domain.BaseUserCommunity;
import kr.co.picTO.community.domain.BaseUserCommunityRepo;
import kr.co.picTO.community.dto.UserCommunityRequestDto;
import kr.co.picTO.community.dto.UserCommunityResponseDto;
import kr.co.picTO.community.exception.CustomCommunityNotExistException;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.local.BaseLocalUserRepo;
import kr.co.picTO.member.domain.social.BaseAuthUser;
import kr.co.picTO.member.domain.social.BaseAuthUserRepo;
import kr.co.picTO.member.exception.CustomUserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserCommunityService {
    private final BaseUserCommunityRepo communityRepo;
    private final BaseLocalUserRepo userJpaRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    public ListResult<UserCommunityResponseDto> findBoardAll() {
        List<BaseUserCommunity> communityList = communityRepo.findAll();
        if(communityList.isEmpty()) {
            throw new CustomCommunityNotExistException();
        }

        List<UserCommunityResponseDto> result = communityList.stream().map(UserCommunityResponseDto::new).collect(Collectors.toList());
        ListResult<UserCommunityResponseDto> listResult = responseService.getListResult(result);
        loggingService.listResultLogging(this.getClass(), "findBoardAll", listResult);

        return listResult;
    }

    public SingleResult<UserCommunityResponseDto> findBoardById(long id) {
        BaseUserCommunity baseUserCommunity = communityRepo.findById(id).orElseThrow(CustomCommunityNotExistException::new);

        UserCommunityResponseDto userCommunityResponseDto = new UserCommunityResponseDto(baseUserCommunity);
        SingleResult<UserCommunityResponseDto> singleResult = responseService.getSingleResult(userCommunityResponseDto);
        loggingService.singleResultLogging(this.getClass(), "findBoardById", singleResult);

        return singleResult;
    }

    public SingleResult<Long> registerBoard(UserCommunityRequestDto userCommunityRequestDto, String provider) {
        Long result = null;
        if(provider != null && provider.equals("LOCAL")) {
            BaseLocalUser blu = userJpaRepo.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepo.save(userCommunityRequestDto.toEntity(blu)).getId();
        } else {
            BaseAuthUser bau = authUserRepo.findByEmail(userCommunityRequestDto.getEmail()).orElseThrow(CustomUserNotFoundException::new);
            result = communityRepo.save(userCommunityRequestDto.toEntity(bau)).getId();
        }
        log.info("User Community SVC registerBoard result : " + result);
        SingleResult<Long> singleResult = responseService.getSingleResult(result);
        loggingService.singleResultLogging(this.getClass(), "registerBoard", singleResult);

        return singleResult;
    }
}
