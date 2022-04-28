package kr.co.picTO.entity.oauth2;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccessTokenId implements Serializable {

    @Column(nullable = false)
    private Long token_id;

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
}
