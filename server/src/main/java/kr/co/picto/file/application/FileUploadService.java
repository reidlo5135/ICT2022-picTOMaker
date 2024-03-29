package kr.co.picto.file.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.picto.common.application.ResponseService;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.file.domain.S3Image;
import kr.co.picto.file.domain.S3ImageRepository;
import kr.co.picto.file.dto.S3ImageRequestDto;
import kr.co.picto.file.dto.S3ImageResponseDto;
import kr.co.picto.file.exception.CustomFileNotFoundException;
import kr.co.picto.token.application.JwtProvider;
import kr.co.picto.user.application.social.SocialUserService;
import kr.co.picto.user.domain.local.User;
import kr.co.picto.user.domain.local.UserRepository;
import kr.co.picto.user.domain.social.SocialUser;
import kr.co.picto.user.domain.social.SocialUserRepository;
import kr.co.picto.user.exception.CustomUserNotFoundException;
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

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Log4j2
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final JwtProvider jwtProvider;
    private final S3ImageRepository s3ImageRepository;
    private final SocialUserRepository socialUserRepository;
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final ResponseService responseService;
    private final SocialUserService socialUserService;

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
    public SingleResult<Long> uploadImage(MultipartFile file, String access_token, String provider) {
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

            S3Image image = null;
            S3ImageRequestDto imageRequestDto = null;
            if(provider.equals("LOCAL")) {
                User user = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new);
                imageRequestDto = new S3ImageRequestDto(user.getEmail(), fileName, fileUrl, getFileExtension(fileName), provider);
                image = imageRequestDto.toBluEntity(user);
                log.info("File SVC uploadImage blu : " + user);
            } else {
                SocialUser socialUser = socialUserRepository.findById(socialUserService.findIdByToken(access_token)).orElseThrow(CustomUserNotFoundException::new);
                imageRequestDto = new S3ImageRequestDto(socialUser.getEmail(), fileName, fileUrl, getFileExtension(fileName), provider);
                image = imageRequestDto.toBauEntity(socialUser);
                log.info("File SVC uploadImage bau : " + socialUser);
            }

            return responseService.getSingleResult(s3ImageRepository.save(image).getId());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("File SVC uploadImage 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
        }
    }

    @Transactional(readOnly = true)
    public ListResult<S3ImageResponseDto> getPicToByEmail(String access_token, String provider) {
        String email = null;
        if(provider.equals("LOCAL")) {
            email = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new).getEmail();
        } else {
            email = socialUserRepository.findById(socialUserService.findIdByToken(access_token)).orElseThrow(CustomUserNotFoundException::new).getEmail();
        }
        List<S3Image> imageList = s3ImageRepository.findByEmailAndProvider(email, provider);
        if(imageList.isEmpty()) throw new CustomFileNotFoundException();

        return responseService.getListResult(imageList.stream().map(S3ImageResponseDto::new).collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public SingleResult<Long> getPicToCountByEmailAndProvider(String access_token, String provider) {
        String email = null;
        if(provider.equals("LOCAL")) {
            email = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new).getEmail();
        } else {
            email = socialUserRepository.findById(socialUserService.findIdByToken(access_token)).orElseThrow(CustomUserNotFoundException::new).getEmail();
        }
        if(s3ImageRepository.findByEmailAndProvider(email, provider).isEmpty()) throw new CustomFileNotFoundException();

        return responseService.getSingleResult(s3ImageRepository.countByEmailAndProvider(email, provider));
    }

    @Transactional
    public SingleResult<Integer> updatePicToByEmailAndId(MultipartFile file, String access_token, Long id) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        log.info("File SVC updatePicToByEmailAndId objectMetaData contentType : " + objectMetadata.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            log.info("File SVC updatePicToByEmailAndId fileName : " + fileName);
            uploadService.uploadFile(inputStream, objectMetadata, fileName);

            String email = userRepository.findById(Long.parseLong(jwtProvider.getUserPk(access_token))).orElseThrow(CustomUserNotFoundException::new).getEmail();
            return responseService.getSingleResult(s3ImageRepository.updateByEmailAndId(uploadService.getFileUrl(fileName), email, id));
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("File SVC updatePicToByEmailAndId 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
        }
    }

    @Transactional
    public SingleResult<Integer> deletePicToById(Long id) {
        return responseService.getSingleResult(s3ImageRepository.deleteByFileId(id));
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
