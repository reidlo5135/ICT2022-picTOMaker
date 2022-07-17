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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

    private final BaseS3ImageRepo imageRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final BaseLocalUserRepo localUserRepo;
    private final UploadService uploadService;

    @Transactional
    public MultipartFile decodeAndConvertFile(Map<String, String> octet) {
        String[] strings = octet.get("image").split(",");
        String extension;
        switch (strings[0]) {
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/jpg;base64":
                extension = "jpg";
                break;
            default:
                extension = "png";
                break;
        }
        try {
            byte[] decoded = DatatypeConverter.parseBase64Binary(strings[1]);
            log.info("File SVC decodeAndConvertFile decoded : " + decoded);

            Date nowTime = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            String formatTime = simpleDateFormat.format(nowTime);

            String path = "C:\\myPicTO\\" + formatTime;
            File folder = new File(path);
            if(!folder.exists()) {
                if(folder.mkdirs()) {
                    log.info("File SVC decodeAndConvertFile folder : "+ folder + " are generated");
                }
            }

            String fileName = decoded + "." + extension;
            File newFile = new File(path, fileName);

            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile))) {
                log.info("File SVC decodeAndConvertFile newFile : " + newFile);
                outputStream.write(decoded);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileItem fileItem = new DiskFileItem("file", Files.probeContentType(newFile.toPath()), false, newFile.getName(), (int)newFile.length(), newFile.getParentFile());
            InputStream input = new FileInputStream(newFile);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);

            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            log.info("File SVC decodeAndConvertFile multipartFile : " + multipartFile);

            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC decodeAndConvertFile error occurred : ", e.getMessage());
        }
        return null;
    }

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
            log.info("File SVC uploadImage fileName : " + fileName);
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
                log.info("File SVC uploadImage blu : " + blu);
                log.info("File SVC uploadImage s3 : " + image);
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
                log.info("File SVC uploadImage bau : " + bau);
                log.info("File SVC uploadImage s3 : " + image);
            }
            imageRepo.save(image);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("FILE SVC uploadImage 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
        }
        return fileUrl;
    }

    public List<BaseS3Image> getPicToByEmail(String email, String provider) {
        List<BaseS3Image> result = new ArrayList<>();
        log.info("File SVC getPicToByEmail email : " + email);
        log.info("File SVC getPicToByEmail provider : " + provider);
        try {
            if(provider.equals("LOCAL")) {
                BaseLocalUser blu = localUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserExistException::new);
                log.info("File SVC getPicToByEmail findByEmail&Prov blu : " + blu);
                email = blu.getEmail();
            } else {
                BaseAuthUser bau = authUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserNotFoundException::new);
                log.info("File SVC getPicToByEmail findByEmail&Prov bau : " + bau);
                email = bau.getEmail();
            }
            result = imageRepo.findAllByEmail(email);
            for(int i=0;i<result.size();i++) {
                log.info("File SVC getPicToByEmail result List : " + Arrays.toString(result.toArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC getPicTOByEmail error occurred : " + e.getMessage());
        }
        return result;
    }

    public Long getPicToCountByEmailAndProvider(String email, String provider) {
        log.info("File SVC getPicToCountByEmailAndProvider email : " + email);
        log.info("File SVC getPicToCountByEmailAndProvider provider : " + provider);
        try {
            Long result = imageRepo.countByEmailAndProvider(email, provider);
            log.info("File SVC getPicToCountByEmailAndProvider result : ", result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC getPicToCountByEmailAndProvider error occurred : " + e.getMessage());
            return -1L;
        }
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
            throw new IllegalArgumentException(String.format("FILE SVC getFileExtension 잘못된 형식의 파일입니다 파일명 -> (%s)", fileName));
        }
    }
}
