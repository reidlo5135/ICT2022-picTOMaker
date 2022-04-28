package kr.co.picTO.entity.local;

import kr.co.picTO.entity.oauth2.BaseAuthRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "user_local")
public class LocalUser {

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
    public LocalUser(String name, String email, BaseAuthRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
