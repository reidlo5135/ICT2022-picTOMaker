package kr.co.picto.user.domain;

import kr.co.picto.common.domain.BaseTimeEntity;
import kr.co.picto.user.TypeEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 100)
    private String provider;

    @Column(nullable = false, length = 100)
    private String skeleton;

    @Column(nullable = false, length = 100)
    private int thick;

    @Column(nullable = false, length = 100)
    private String lineColor;

    @Column(nullable = false, length = 100)
    private String backgroundColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEnum type;
}
