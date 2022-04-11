package kr.co.picTO.service.security;

import kr.co.picTO.entity.oauth.BaseAuthUser;
import kr.co.picTO.entity.oauth.OAuthAttributes;
import kr.co.picTO.entity.oauth.SessionUser;
import kr.co.picTO.repository.BaseAuthUserRepo;
import kr.co.picTO.repository.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Log4j2
public class BaseCustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final BaseAuthUserRepo baseAuthUserRepo;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        BaseAuthUser authUser = saveOrUpdate(oAuthAttributes);
        httpSession.setAttribute("user", new SessionUser(authUser));

        log.info("{}" + authUser.toString());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(authUser.getRoleKey())), oAuthAttributes.getAttributes(), oAuthAttributes.getNameAttributeKey()
        );
    }

    private BaseAuthUser saveOrUpdate(OAuthAttributes attributes) {
        BaseAuthUser authUser = baseAuthUserRepo.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());

        return baseAuthUserRepo.save(authUser);
    }
}
