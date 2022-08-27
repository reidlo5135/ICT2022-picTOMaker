package kr.co.picTO.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.picTO.common.domain.BaseTimeEntity;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.social.BaseAuthUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "base_user_community")
public class BaseUserCommunity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = BaseLocalUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "blu_id")
    private BaseLocalUser blu;

    @JsonIgnore
    @ManyToOne(targetEntity = BaseAuthUser.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bau_id")
    private BaseAuthUser bau;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
