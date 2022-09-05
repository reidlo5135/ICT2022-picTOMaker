package kr.co.picTO.file.dto;

import kr.co.picTO.file.domain.BaseS3Image;
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
        if(baseS3Image.getUser() != null) {
            email = baseS3Image.getUser().getEmail();
        } else if(baseS3Image.getSocialUser() != null) {
            email = baseS3Image.getSocialUser().getEmail();
        }
        id = baseS3Image.getId();
        fileName = baseS3Image.getFileName();
        fileUrl = baseS3Image.getFileUrl();
        createdDate = baseS3Image.getCreatedDate();
        modifiedDate = baseS3Image.getModifiedDate();
    }
}
