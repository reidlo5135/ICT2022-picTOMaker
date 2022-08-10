package kr.co.picTO.community.application;

import kr.co.picTO.community.domain.BaseCommunityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommunityService {
    private static final String className = CommunityService.class.toString();
    private BaseCommunityRepo communityRepo;
}
