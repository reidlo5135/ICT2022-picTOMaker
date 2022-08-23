package kr.co.picTO.s3.dto;

import kr.co.picTO.s3.domain.BaseS3Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class S3ImageResponseDto {

    private Long id;
    private String email;
    private String fileName;
    private String fileUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public S3ImageResponseDto(BaseS3Image baseS3Image) {
        if(baseS3Image.getBlu() != null) {
            email = baseS3Image.getBlu().getEmail();
        } else if(baseS3Image.getBau() != null) {
            email = baseS3Image.getBau().getEmail();
        }
        id = baseS3Image.getId();
        fileName = baseS3Image.getFileName();
        fileUrl = baseS3Image.getFileUrl();
        createdDate = baseS3Image.getCreatedDate();
        modifiedDate = baseS3Image.getModifiedDate();
    }
}
