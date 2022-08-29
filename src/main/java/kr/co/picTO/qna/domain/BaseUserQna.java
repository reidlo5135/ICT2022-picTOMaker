package kr.co.picTO.qna.domain;

import kr.co.picTO.common.domain.BaseTimeEntity;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.social.BaseAuthUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "base_user_qna")
public class BaseUserQna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = BaseLocalUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "blu_id")
    private BaseLocalUser blu;

    @ManyToOne(targetEntity = BaseAuthUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bau_id")
    private BaseAuthUser bau;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String qna;
}
