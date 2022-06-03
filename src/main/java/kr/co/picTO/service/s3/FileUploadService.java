package kr.co.picTO.service.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.picTO.entity.s3.BaseS3Image;
import kr.co.picTO.repository.BaseS3ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

    private final BaseS3ImageRepo imageRepo;
    private final UploadService uploadService;

    public String uploadImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        log.info("File SVC uploadImg objectMetaData contentType : " + objectMetadata.getContentType());

        String fileUrl = null;

        try (InputStream inputStream = file.getInputStream()) {
            log.info("File SVC uploadImg fileName : " + fileName);
            uploadService.uploadFile(inputStream, objectMetadata, fileName);
            fileUrl = uploadService.getFileUrl(fileName);

            BaseS3Image image = BaseS3Image.builder()
                    .fileName(fileName)
                    .fileUrl(fileUrl)
                    .extension(getFileExtension(fileName))
                    .build();
            imageRepo.save(image);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다 (%s)", file.getOriginalFilename()));
        }
        return fileUrl;
    }

    private String createFileName(String originalFileName) {
        log.info("File SVC createFileName originalFileName : " + originalFileName);
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            String ext = fileName.substring(fileName.lastIndexOf("."));
            log.info("File SVC getFileExtension fName : " + fileName);
            log.info("File SVC getFileExtension Ext : " + ext);
            return ext;
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }
}
