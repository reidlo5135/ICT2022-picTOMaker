package kr.co.picTO.file.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.picTO.common.application.ResponseService;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.file.exception.CustomFileNotFoundException;
import kr.co.picTO.file.domain.BaseS3Image;
import kr.co.picTO.file.domain.BaseS3ImageRepo;
import kr.co.picTO.file.dto.S3ImageRequestDto;
import kr.co.picTO.file.dto.S3ImageResponseDto;
import kr.co.picTO.user.domain.local.BaseLocalUser;
import kr.co.picTO.user.domain.local.BaseLocalUserRepo;
import kr.co.picTO.user.domain.social.BaseAuthUser;
import kr.co.picTO.user.domain.social.BaseAuthUserRepo;
import kr.co.picTO.user.exception.CustomUserNotFoundException;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final BaseS3ImageRepo imageRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final BaseLocalUserRepo localUserRepo;
    private final UploadService uploadService;
    private final ResponseService responseService;

    @Transactional
    public MultipartFile decodeAndConvertFile(String octet) {
        try {
            String[] strings = octet.split(",");
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
            log.error("File SVC decodeAndConvertFile error occurred : " + e.getMessage());
        }
        return null;
    }

    @Transactional
    public SingleResult<Long> uploadImage(MultipartFile file, String email, String provider) {
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
            S3ImageRequestDto imageRequestDto = null;
            if(provider.equals("LOCAL")) {
                BaseLocalUser blu = localUserRepo.findByEmail(email).orElseThrow();
                imageRequestDto = new S3ImageRequestDto(blu.getEmail(), fileName, fileUrl, getFileExtension(fileName), provider);
                image = imageRequestDto.toBluEntity(blu);
                log.info("File SVC uploadImage blu : " + blu);
            } else {
                BaseAuthUser bau = authUserRepo.findByEmail(email).orElseThrow();
                imageRequestDto = new S3ImageRequestDto(bau.getEmail(), fileName, fileUrl, getFileExtension(fileName), provider);
                image = imageRequestDto.toBauEntity(bau);
                log.info("File SVC uploadImage bau : " + bau);
            }

            return responseService.getSingleResult(imageRepo.save(image).getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("File SVC uploadImage 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
        }
    }

    @Transactional(readOnly = true)
    public ListResult<S3ImageResponseDto> getPicToByEmail(String email, String provider) {
        if(provider.equals("LOCAL")) {
            localUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserNotFoundException::new);
        } else {
            authUserRepo.findByEmailAndProvider(email, provider).orElseThrow(CustomUserNotFoundException::new);
        }
        List<BaseS3Image> imageList = imageRepo.findByEmailAndProvider(email, provider);
        if(imageList.isEmpty()) throw new CustomFileNotFoundException();

        return responseService.getListResult(imageList.stream().map(S3ImageResponseDto::new).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public SingleResult<Long> getPicToCountByEmailAndProvider(String email, String provider) {
        if(imageRepo.findByEmailAndProvider(email, provider).isEmpty()) throw new CustomFileNotFoundException();

        return responseService.getSingleResult(imageRepo.countByEmailAndProvider(email, provider));
    }

    @Transactional
    public SingleResult<Integer> updatePicToByEmailAndId(MultipartFile file, String email, Long id) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        log.info("File SVC updatePicToByEmailAndId objectMetaData contentType : " + objectMetadata.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            log.info("File SVC updatePicToByEmailAndId fileName : " + fileName);
            uploadService.uploadFile(inputStream, objectMetadata, fileName);

            return responseService.getSingleResult(imageRepo.updateByEmailAndId(uploadService.getFileUrl(fileName), email, id));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("File SVC updatePicToByEmailAndId 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
        }
    }

    @Transactional
    public SingleResult<Integer> deletePicToById(Long id) {
        return responseService.getSingleResult(imageRepo.deleteByFileId(id));
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("FILE SVC getFileExtension 잘못된 형식의 파일입니다 파일명 -> (%s)", fileName));
        }
    }
}
