package kr.co.picto.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.picTO.common.domain.BaseTimeEntity;
import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.social.SocialUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_community")
public class UserCommunity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(targetEntity = SocialUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "social_user_id")
    private SocialUser socialUser;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
