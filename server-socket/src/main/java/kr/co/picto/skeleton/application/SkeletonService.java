package kr.co.picto.skeleton.application;

import kr.co.picto.skeleton.TypeEnum;
import kr.co.picto.skeleton.domain.SkeletonRepository;
import kr.co.picto.skeleton.dto.SkeletonCreateDto;
import kr.co.picto.skeleton.dto.SkeletonInfoDto;
import kr.co.picto.user.domain.User;
import kr.co.picto.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Log4j2
@Service
@RequiredArgsConstructor
public class SkeletonService {
    private final SkeletonRepository skeletonRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public JSONObject select(JSONObject jsonObject) {
        User user = userRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);
        if(skeletonRepository.findByUser(user).isEmpty()) return null;
        SkeletonInfoDto skeletonInfoDto = SkeletonInfoDto.from(skeletonRepository.findByUser(user).orElseThrow(NullPointerException::new));

        return skeletonInfoDto.toJSON(skeletonInfoDto);
    }

    @Transactional
    public void save(JSONObject jsonObject) {
       log.info("SkeletonService save jsonObject : " + jsonObject);
        SkeletonCreateDto skeletonCreateDto =
                new SkeletonCreateDto(
                        jsonObject.get("skeleton").toString(),
                        Integer.parseInt(jsonObject.get("thick").toString()),
                        jsonObject.get("lineColor").toString(),
                        jsonObject.get("backgroundColor").toString(),
                        jsonObject.get("type").toString().toUpperCase(Locale.ROOT)
                );
        User user = userRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);

        if(skeletonRepository.findByUser(user).isPresent()) {
            skeletonRepository.update(jsonObject.get("skeleton").toString(), user, TypeEnum.valueOf(jsonObject.get("type").toString().toUpperCase(Locale.ROOT)));
        } else {
            skeletonRepository.save(skeletonCreateDto.toEntity(user));
        }
    }
}
