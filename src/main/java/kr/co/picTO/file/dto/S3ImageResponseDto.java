package kr.co.picTO.file.dto;

import kr.co.picTO.file.domain.S3Image;
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

    public S3ImageResponseDto(S3Image s3Image) {
        if(s3Image.getUser() != null) {
            email = s3Image.getUser().getEmail();
        } else if(s3Image.getSocialUser() != null) {
            email = s3Image.getSocialUser().getEmail();
        }
        id = s3Image.getId();
        fileName = s3Image.getFileName();
        fileUrl = s3Image.getFileUrl();
        createdDate = s3Image.getCreatedDate();
        modifiedDate = s3Image.getModifiedDate();
    }
}
