package kr.co.picTO.dto.local;

import kr.co.picTO.entity.local.BaseLocalUser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LocalUserLoginResponseDto {
    private final Long id;
    private final List<String> roles;
    private final LocalDateTime createdDate;

    public LocalUserLoginResponseDto(BaseLocalUser user) {
        this.id = user.getId();
        this.roles = user.getRoles();
        this.createdDate = user.getCreatedDate();
    }
}
