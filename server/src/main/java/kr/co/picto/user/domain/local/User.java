package kr.co.picto.user.domain.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.picto.common.domain.BaseTimeEntity;
import kr.co.picto.user.domain.AccountStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity implements UserDetails {
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_PATTERN = "[a-zA-Z0-9!@#$%^&*()_+\\-=]{8,30}";
    private static final String NICK_NAME_PATTERN = "[a-zA-Z0-9가-힣]{2,20}";

    private static final String EMAIL_ERROR_MESSAGE = "이메일 형식에 맞지 않습니다.";
    private static final String PASSWORD_ERROR_MESSAGE = "비밀번호는 8 ~ 30자";
    private static final String NICK_NAME_ERROR_MESSAGE = "닉네임은 2 ~ 20자 한글 숫자 영어";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_ERROR_MESSAGE)
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR_MESSAGE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Pattern(regexp = NICK_NAME_PATTERN, message = NICK_NAME_ERROR_MESSAGE)
    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(length = 100)
    private String provider;

    @Column(length = 455)
    private String profile_image_url;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void update(User updateUser) {
        this.nickName = updateUser.nickName;
        this.profile_image_url = updateUser.profile_image_url;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
