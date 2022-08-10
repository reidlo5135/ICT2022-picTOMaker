package kr.co.picTO.s3.application;

import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.co.picTO.common.exception.CustomUserExistException;
import kr.co.picTO.common.exception.CustomUserNotFoundException;
import kr.co.picTO.member.domain.local.BaseLocalUser;
import kr.co.picTO.member.domain.oauth2.BaseAuthUser;
import kr.co.picTO.s3.domain.BaseS3Image;
import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.member.domain.BaseAuthUserRepo;
import kr.co.picTO.member.domain.BaseLocalUserRepo;
import kr.co.picTO.s3.domain.BaseS3ImageRepo;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.common.application.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final String className = FileUploadService.class.toString();

    private final BaseS3ImageRepo imageRepo;
    private final BaseAuthUserRepo authUserRepo;
    private final BaseLocalUserRepo localUserRepo;
    private final UploadService uploadService;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

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
            log.error("File SVC decodeAndConvertFile error occurred : ", e.getMessage());
        }
        return null;
    }

    @Transactional
    public ResponseEntity<?> uploadImage(MultipartFile file, String email, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("File SVC uploadImage email : " + email);
        log.info("File SVC uploadImage provider : " + provider);

        try {
            if(file == null || file.isEmpty()) {
                CommonResult failResult = responseService.getFailResult(-1, "uploadImage Error Occurred : File is Null");
                loggingService.commonResultLogging(className, "uploadImage", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
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

                    SingleResult<String> singleResult = responseService.getSingleResult(fileUrl);
                    loggingService.singleResultLogging(className, "uploadImage", singleResult);
                    ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
                } catch (IOException e) {
                    throw new IllegalArgumentException(String.format("File SVC uploadImage 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC uploadImage Error Occurred : " + e.getMessage());
        } finally {
            log.info("File SVC uploadImage ett : " + ett);
            return ett;
        }
    }

    @Transactional
    public ResponseEntity<?> updatePicToByEmailAndId(MultipartFile file, String email, Long id) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("File SVC updatePicToByEmailAndId email : " + email);
        log.info("File SVC updatePicToByEmailAndId id : " + id);
        try {
            if(file == null || file.isEmpty()) {
                CommonResult failResult = responseService.getFailResult(-1, "updatePicToByEmailAndId Error Occurred");
                loggingService.commonResultLogging(className, "updatePicToByEmailAndId", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Integer result = null;
                String fileName = createFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                log.info("File SVC updatePicToByEmailAndId objectMetaData contentType : " + objectMetadata.getContentType());

                String fileUrl = null;

                try (InputStream inputStream = file.getInputStream()) {
                    log.info("File SVC updatePicToByEmailAndId fileName : " + fileName);
                    uploadService.uploadFile(inputStream, objectMetadata, fileName);
                    fileUrl = uploadService.getFileUrl(fileName);

                    result = imageRepo.updateByEmailAndId(fileUrl, email, id);

                    SingleResult<Integer> singleResult = responseService.getSingleResult(result);
                    loggingService.singleResultLogging(className, "updatePicToByEmailAndId", singleResult);
                    ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
                } catch (IOException e) {
                    throw new IllegalArgumentException(String.format("File SVC updatePicToByEmailAndId 파일 변환 중 에러가 발생했습니다 파일명 -> (%s) : ", file.getOriginalFilename()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC updatePicToByEmailAndId Error Occurred : " + e.getMessage());
        } finally {
            log.info("File SVC updatePicToByEmailAndId ett : " + ett);
            return ett;
        }
    }

    public ResponseEntity<?> getPicToByEmail(String email, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<BaseS3Image> result = new ArrayList<>();

        log.info("File SVC getPicToByEmail email : " + email);
        log.info("File SVC getPicToByEmail provider : " + provider);

        try {
            if(email == null || provider == null) {
                CommonResult failResult = responseService.getFailResult(-1, "getPicToByEmail Error Occurred");
                loggingService.commonResultLogging(className, "getPicToByEmail", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
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
                ListResult listResult = responseService.getListResult(result);
                loggingService.listResultLogging(className, "getPicToByEmail", listResult);
                ett = new ResponseEntity<>(listResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC getPicTOByEmail Error Occurred : " + e.getMessage());
        } finally {
            log.info("File SVC getPicToByEmail ett : " + ett);
            return ett;
        }
    }

    public ResponseEntity<?> getPicToCountByEmailAndProvider(String email, String provider) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("File SVC getPicToCountByEmailAndProvider email : " + email);
        log.info("File SVC getPicToCountByEmailAndProvider provider : " + provider);

        try {
            if(email == null || provider == null) {
                CommonResult failResult = responseService.getFailResult(-1, "getPicToCountByEmailAndProvider Error Occurred");
                loggingService.commonResultLogging(className, "getPicToCountByEmailAndProvider", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Long result = imageRepo.countByEmailAndProvider(email, provider);
                log.info("File SVC getPicToCountByEmailAndProvider result : ", result);

                SingleResult<Long> singleResult = responseService.getSingleResult(result);
                loggingService.singleResultLogging(className, "getPicToCountByEmailAndProvider", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC getPicToCountByEmailAndProvider Error Occurred : " + e.getMessage());
        } finally {
            log.info("File SVC getPicToByCountByEmailAndProvider ett : " + ett);
            return ett;
        }
    }

    @Transactional
    public ResponseEntity<?> deletePicToById(Long id) {
        ResponseEntity<?> ett = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("File SVC deletePicToById id : " + id);
        try {
            if(id == null) {
                CommonResult failResult = responseService.getFailResult(-1, "deletePicToById Error Occurred");
                loggingService.commonResultLogging(className, "deletePicToById", failResult);
                ett = new ResponseEntity<>(failResult, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                Integer result = imageRepo.deleteByFileName(id);
                log.info("File SVC deletePicToById result : ", result);

                SingleResult<Integer> singleResult = responseService.getSingleResult(result);
                loggingService.singleResultLogging(className, "deletePicToById", singleResult);
                ett = new ResponseEntity<>(singleResult, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File SVC deletePicToById Error Occurred : " + e.getMessage());
        } finally {
            log.info("File SVC deletePicToById ett : " + ett);
            return ett;
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
