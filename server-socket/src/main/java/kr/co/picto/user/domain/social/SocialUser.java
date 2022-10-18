package kr.co.picto.user.domain.social;

import kr.co.picto.common.domain.BaseTimeEntity;
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

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String provider;
}
