package kr.co.picto.user.dto.local;

import kr.co.picto.user.domain.local.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final String nickName;
    private final List<String> roles;
    private final Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime modifiedDate;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.roles = user.getRoles();
        this.authorities = user.getAuthorities();
        this.modifiedDate = user.getModifiedDate();
    }
}
