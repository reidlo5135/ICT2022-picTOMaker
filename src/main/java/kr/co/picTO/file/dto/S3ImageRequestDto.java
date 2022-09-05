package kr.co.picTO.file.dto;

import kr.co.picTO.file.domain.BaseS3Image;
import kr.co.picTO.user.domain.User;
import kr.co.picTO.user.domain.social.SocialUser;
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

    public BaseS3Image toUser(User user) {
        return BaseS3Image.builder()
                .user(user)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }

    public BaseS3Image toSocialUser(SocialUser socialUser) {
        return BaseS3Image.builder()
                .socialUser(socialUser)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }

//    public BaseS3Image toBluEntity(BaseLocalUser blu) {
//        return BaseS3Image.builder()
//                .blu(blu)
//                .email(email)
//                .fileName(fileName)
//                .fileUrl(fileUrl)
//                .extension(extension)
//                .provider(provider)
//                .build();
//    }
//
//    public BaseS3Image toBauEntity(SocialUser bau) {
//        return BaseS3Image.builder()
//                .bau(bau)
//                .email(email)
//                .fileName(fileName)
//                .fileUrl(fileUrl)
//                .extension(extension)
//                .provider(provider)
//                .build();
//    }
}
