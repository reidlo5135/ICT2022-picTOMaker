package kr.co.picto.skeleton.application;

import kr.co.picto.skeleton.TypeEnum;
import kr.co.picto.skeleton.domain.SkeletonRepository;
import kr.co.picto.skeleton.dto.SkeletonCreateDto;
import kr.co.picto.skeleton.dto.SkeletonInfoDto;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.domain.local.User;
import kr.co.picto.user.domain.local.UserRepository;
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
    private final SocialUserRepository socialUserRepository;

    @Transactional(readOnly = true)
    public JSONObject select(JSONObject jsonObject) {
        SkeletonInfoDto skeletonInfoDto = null;
        if(jsonObject.get("provider").toString().equals("LOCAL")) {
            User user = userRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);
            if(skeletonRepository.findByUser(user).isEmpty()) return null;
            skeletonInfoDto = SkeletonInfoDto.from(skeletonRepository.findByUser(user).orElseThrow(NullPointerException::new));
        } else {
            SocialUser socialUser = socialUserRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);
            if(skeletonRepository.findBySocialUser(socialUser).isEmpty()) return null;
            skeletonInfoDto = SkeletonInfoDto.from(skeletonRepository.findBySocialUser(socialUser).orElseThrow(NullPointerException::new));
        }

        return skeletonInfoDto.toJSON(skeletonInfoDto);
    }

    @Transactional
    public void save(JSONObject jsonObject) {
        SkeletonCreateDto skeletonCreateDto =
                new SkeletonCreateDto(
                        jsonObject.get("skeleton").toString(),
                        Integer.parseInt(jsonObject.get("thick").toString()),
                        jsonObject.get("lineColor").toString(),
                        jsonObject.get("backgroundColor").toString(),
                        jsonObject.get("type").toString().toUpperCase(Locale.ROOT)
                );
        if(jsonObject.get("provider").toString().equals("LOCAL")) {
            User user = userRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);
            if(skeletonRepository.findByUser(user).isPresent()) {
                skeletonRepository.update(jsonObject.get("skeleton").toString(), user, TypeEnum.valueOf(jsonObject.get("type").toString().toUpperCase(Locale.ROOT)));
            } else {
                skeletonRepository.save(skeletonCreateDto.toEntity(user));
            }
        } else {
            SocialUser socialUser = socialUserRepository.findByEmailAndProvider(jsonObject.get("email").toString(), jsonObject.get("provider").toString()).orElseThrow(NullPointerException::new);
            if(skeletonRepository.findBySocialUser(socialUser).isPresent()) {
                skeletonRepository.update(jsonObject.get("skeleton").toString(), socialUser, TypeEnum.valueOf(jsonObject.get("type").toString().toUpperCase(Locale.ROOT)));
            } else {
                skeletonRepository.save(skeletonCreateDto.toEntity(socialUser));
            }
        }
    }
}
