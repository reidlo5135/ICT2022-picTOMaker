package kr.co.picTO.member.domain.oauth2;

import kr.co.picTO.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "base_access_token")
public class BaseAccessToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long local_user_id;

    @Column(nullable = false)
    private String access_token;

    @Column(nullable = false)
    private String token_type;

    @Column(nullable = false)
    private String refresh_token;

    @Column(nullable = false)
    private long expires_in;

    @Column(nullable = false)
    private long refresh_token_expires_in;

    @Column(nullable = false)
    private String provider;

    public void refreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
