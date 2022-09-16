package kr.co.picto.user.dto.social;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleProfile {
    private String email;
    private String name;
    private String picture;
}
