package kr.co.picTO.entity.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "base_access_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;

    @Column(nullable = false)
    private String access_token;

    @Column(nullable = false)
    private String token_type;

    @Column(nullable = true)
    private String refresh_token;

    @Column(nullable = false)
    private long expires_in;

    @Column(nullable = true)
    private long refresh_token_expires_in;

    @Column(nullable = false)
    private String provider;

    @Builder
    public AccessToken(String access_token, String token_type, String refresh_token, long expires_in, long refresh_token_expires_in, String provider) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.refresh_token_expires_in = refresh_token_expires_in;
        this.provider = provider;
    }
}
