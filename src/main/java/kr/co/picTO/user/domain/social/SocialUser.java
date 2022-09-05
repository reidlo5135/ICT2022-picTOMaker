package kr.co.picTO.user.domain.social;

import kr.co.picTO.common.domain.BaseTimeEntity;
import kr.co.picTO.user.domain.AccountRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "social_user")
public class SocialUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(nullable = false)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @Builder
    public SocialUser(String name, String email, String picture, String provider, AccountRole role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.role = role;
    }

    public SocialUser update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
