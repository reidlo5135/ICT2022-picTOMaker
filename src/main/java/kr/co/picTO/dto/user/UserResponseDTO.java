package kr.co.picTO.dto.user;

import kr.co.picTO.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDTO {
    private final Long userId;
    private final String email;
    private final String name;

    public UserResponseDTO(User user) {
        this.userId = user.getUserid();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
