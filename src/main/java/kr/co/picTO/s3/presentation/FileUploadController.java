package kr.co.picTO.s3.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.s3.application.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(tags = {"5. File Upload Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/picTO")
public class FileUploadController {

    private static final String className = FileUploadController.class.toString();

    private final FileUploadService fileUploadService;
    private final ResponseLoggingService loggingService;

    @PostMapping(value = "/register/{email}/{provider}")
    public ResponseEntity<?> uploadFile(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "uploadFile", octet.get("image"), email, provider);
        try {
            MultipartFile multipartFile = fileUploadService.decodeAndConvertFile(octet.get("image"));
            log.info("File Upload Controller uploadFile multipartFile : " + multipartFile);

            ett = fileUploadService.uploadImage(multipartFile, email, provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller uploadFile error occurred : " + e.getMessage());
        } finally {
            log.info("File Upload Controller uploadFile ett : " + ett);
            return ett;
        }
    }

    @PutMapping(value = "/update/{email}/{id}")
    public ResponseEntity<?> updatePicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable Long id) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "updatePicTo", octet.get("octet"), email, String.valueOf(id));
        try {
            MultipartFile multipartFile = fileUploadService.decodeAndConvertFile(octet.get("octet"));
            log.info("File Upload Controller updatePicTo multipartFile : " + multipartFile);

            ett = fileUploadService.updatePicToByEmailAndId(multipartFile, email, id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller updatePicTo error occurred : " + e.getMessage());
        } finally {
            log.info("File Upload Controller updatePicTo ett : " + ett);
            return ett;
        }
    }

    @PostMapping(value = "/get/{email}/{provider}")
    public ResponseEntity<?> getPicTo(@PathVariable String email, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "getPicTo", email, provider, "");
        try {
            ett = fileUploadService.getPicToByEmail(email, provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller getPicTo error occurred : " + e.getMessage());
        } finally {
            log.info("File Upload Controller getPicTo ett : " + ett);
            return ett;
        }
    }

    @PostMapping(value = "/count/{email}/{provider}")
    public ResponseEntity<?> getPicToCount(@PathVariable String email, @PathVariable String provider) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "getPicToCount", email, provider, "");
        try {
            ett = fileUploadService.getPicToCountByEmailAndProvider(email, provider);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller getPicToCount error occurred : " + e.getMessage());
        } finally {
            log.info("File Upload Controller getPicToCount ett : " + ett);
            return ett;
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePicTo(@PathVariable Long id) {
        ResponseEntity<?> ett = null;
        loggingService.httpPathStrLogging(className, "deletePicTo", String.valueOf(id), "", "");
        try {
            ett = fileUploadService.deletePicToById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller deletePicTo error occurred : " + e.getMessage());
        } finally {
            log.info("File Upload Controller deletePicTo ett : " + ett);
            return ett;
        }
    }
}
