package kr.co.picTO.dto.user;

import kr.co.picTO.entity.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserResponseDTO {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickName;
    private List<String> roles;
    private Collection<? extends GrantedAuthority> authorities;
    private final LocalDateTime modifiedDate;

    public UserResponseDTO(User user) {
        this.userId = user.getUserid();
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.roles = user.getRoles();
        this.authorities = user.getAuthorities();
        this.modifiedDate = user.getModifiedDate();
    }
}
