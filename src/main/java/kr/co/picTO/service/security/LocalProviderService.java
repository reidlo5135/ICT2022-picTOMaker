package kr.co.picTO.service.security;

import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class LocalProviderService {

    private final BaseLocalUserRepo userRepo;
    private final BaseTokenRepo tokenRepo;
}
