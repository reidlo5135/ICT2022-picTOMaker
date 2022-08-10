package kr.co.picTO.community.presentation;

import kr.co.picTO.community.application.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/community")
public class CommunityController {
    private static final String className = CommunityController.class.toString();
    private final CommunityService communityService;
}
