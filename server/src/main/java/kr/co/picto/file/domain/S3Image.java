package kr.co.picto.file.domain;

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
@Table(name = "s3_image")
public class S3Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = SocialUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "social_user_id")
    private SocialUser socialUser;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private String provider;
}
