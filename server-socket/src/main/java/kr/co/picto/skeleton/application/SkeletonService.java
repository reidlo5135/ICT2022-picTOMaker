package kr.co.picto.skeleton.application;

import kr.co.picto.skeleton.domain.SkeletonRepository;
import kr.co.picto.skeleton.dto.SkeletonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkeletonService {
    private final SkeletonRepository skeletonRepository;

}
