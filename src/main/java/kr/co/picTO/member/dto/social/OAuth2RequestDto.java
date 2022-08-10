package kr.co.picTO.member.dto.social;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class OAuth2RequestDto {
    private String url;
    private LinkedMultiValueMap<String, String> map;
}
