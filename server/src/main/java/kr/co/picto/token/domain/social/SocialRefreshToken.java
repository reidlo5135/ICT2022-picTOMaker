package kr.co.picto.token.domain.social;

import kr.co.picto.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "social_refresh_token")
public class SocialRefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tokenId;

    @Column(nullable = false)
    private String token;

    public SocialRefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public SocialRefreshToken(Long tokenId, String token) {
        this.tokenId = tokenId;
        this.token = token;
    }
}
