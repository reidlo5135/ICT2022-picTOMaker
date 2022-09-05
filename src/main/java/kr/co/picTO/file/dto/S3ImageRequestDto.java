package kr.co.picTO.file.dto;

import kr.co.picTO.user.domain.local.User;
import kr.co.picTO.user.domain.social.SocialUser;
import kr.co.picTO.file.domain.S3Image;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class S3ImageRequestDto {
    private String email;
    private String fileName;
    private String fileUrl;
    private String extension;
    private String provider;

    public S3Image toBluEntity(User user) {
        return S3Image.builder()
                .user(user)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }

    public S3Image toBauEntity(SocialUser socialUser) {
        return S3Image.builder()
                .socialUser(socialUser)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }
}
