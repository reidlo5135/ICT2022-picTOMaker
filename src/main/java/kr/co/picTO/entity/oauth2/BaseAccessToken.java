package kr.co.picTO.entity.oauth2;

import kr.co.picTO.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "base_access_token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccessToken extends BaseTimeEntity {

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
}
