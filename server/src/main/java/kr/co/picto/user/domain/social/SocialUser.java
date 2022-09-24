package kr.co.picto.user.domain.social;

import kr.co.picto.common.domain.BaseTimeEntity;
import kr.co.picto.user.domain.AccountRole;
import kr.co.picto.user.domain.AccountStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }
}
