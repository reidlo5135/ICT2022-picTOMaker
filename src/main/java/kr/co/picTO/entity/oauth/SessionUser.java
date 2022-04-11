package kr.co.picTO.entity.oauth;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(BaseAuthUser authUser) {
        this.name = authUser.getName();
        this.email = authUser.getEmail();
        this.picture = authUser.getPicture();
    }
}
