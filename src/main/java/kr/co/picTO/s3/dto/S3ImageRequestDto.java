package kr.co.picTO.s3.dto;

import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAuthUser;
import kr.co.picTO.s3.domain.BaseS3Image;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class S3ImageRequestDto {
    private String email;
    private String fileName;
    private String fileUrl;
    private String extension;
    private String provider;

    public BaseS3Image toBluEntity(BaseLocalUser blu) {
        return BaseS3Image.builder()
                .blu(blu)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }

    public BaseS3Image toBauEntity(BaseAuthUser bau) {
        return BaseS3Image.builder()
                .bau(bau)
                .email(email)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .extension(extension)
                .provider(provider)
                .build();
    }
}
