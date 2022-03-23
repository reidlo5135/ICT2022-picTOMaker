package kr.co.picTO.dto.user;

import kr.co.picTO.entity.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLoginResponseDTO {

    private final Long userId;
    private final List<String> roles;
    private final LocalDateTime createdDate;

    public UserLoginResponseDTO(User user) {
        this.userId = user.getUserid();
        this.roles = user.getRoles();
        this.createdDate = user.getCreatedDate();
    }
}
