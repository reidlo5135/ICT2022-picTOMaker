package kr.co.picTO.entity.local;

import kr.co.picTO.entity.BaseTimeEntity;
import kr.co.picTO.entity.oauth2.BaseAuthRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BaseLocalUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseAuthRole role;

    @Builder
    public BaseLocalUser(String name, String email, BaseAuthRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
