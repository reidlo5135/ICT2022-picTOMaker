package kr.co.picTO.service.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.picTO.advice.exception.CustomUserExistException;
import kr.co.picTO.advice.exception.CustomUserNotFoundException;
import kr.co.picTO.entity.local.BaseLocalUser;
import kr.co.picTO.entity.oauth2.BaseAuthUser;
import kr.co.picTO.entity.s3.BaseS3Image;
import kr.co.picTO.repository.BaseAuthUserRepo;
import kr.co.picTO.repository.BaseLocalUserRepo;
import kr.co.picTO.repository.BaseS3ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

    private final BaseS3ImageRepo imageRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final BaseLocalUserRepo localUserRepo;
    private final UploadService uploadService;

    @Transactional
    public String uploadImage(MultipartFile file, String email, String provider) {
        log.info("File SVC uploadImage email : " + email);
        log.info("File SVC uploadImage provider : " + provider);

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

            BaseS3Image image = null;
            if(provider.equals("LOCAL")) {
                BaseLocalUser blu = localUserRepo.findByEmail(email).orElseThrow();
                image = BaseS3Image.builder()
                       .blu(blu)
                       .email(blu.getEmail())
                       .fileName(fileName)
                       .fileUrl(fileUrl)
                       .extension(getFileExtension(fileName))
                       .provider(provider)
                       .build();
                log.info("File SVC uploadImg blu : " + blu);
                log.info("File SVC uploadImg s3 : " + image);
            } else {
                BaseAuthUser bau = authUserRepo.findByEmail(email).orElseThrow();
                image = BaseS3Image.builder()
                       .bau(bau)
                       .email(bau.getEmail())
                       .fileName(fileName)
                       .fileUrl(fileUrl)
                       .extension(getFileExtension(fileName))
                       .provider(provider)
                       .build();
                log.info("File SVC uploadImg bau : " + bau);
                log.info("File SVC uploadImg s3 : " + image);
            }
            imageRepo.save(image);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다 (%s) : ", file.getOriginalFilename()));
        }
        return fileUrl;
    }

    public List<String> getPicToByEmail(String email, String provider) {
        BaseS3Image baseS3Image = null;
        List<String> result = new ArrayList<>();
        try {
            if(provider.equals("LOCAL")) {
                BaseLocalUser blu = localUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserExistException::new);
                log.info("File SVC getPicTo findByEmail&Prov blu : " + blu);
                email = blu.getEmail();
            } else {
                BaseAuthUser bau = authUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserNotFoundException::new);
                log.info("File SVC getPicTo findByEmail&Prov bau : " + bau);
                email = bau.getEmail();
            }
            baseS3Image = imageRepo.findByEmail(email).orElseThrow();
            result.add(baseS3Image.getFileUrl());
            log.info("File SVC getPicTo bSI : " + baseS3Image);
            log.info("File SVC getPicTo result List : " + result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC getPicTOByEmail : " + e.getMessage());
        }
        return result;
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
