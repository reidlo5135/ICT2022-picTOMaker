package kr.co.picto.skeleton.domain;

import kr.co.picto.common.domain.BaseTimeEntity;
import kr.co.picto.skeleton.TypeEnum;
import kr.co.picto.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skeleton")
public class Skeleton extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 4000)
    private String coordinate;

    @Column(nullable = false, length = 100)
    private int thick;

    @Column(nullable = false, length = 100)
    private String lineColor;

    @Column(nullable = false, length = 100)
    private String backgroundColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "picto_type", nullable = false)
    private TypeEnum type;
}
