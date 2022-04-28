package kr.co.picTO.entity.oauth2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {

    @Id
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
