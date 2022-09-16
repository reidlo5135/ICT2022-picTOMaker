package kr.co.picto.file.dto;

import kr.co.picto.file.domain.S3Image;
import kr.co.picto.user.domain.local.User;
import kr.co.picto.user.domain.social.SocialUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
