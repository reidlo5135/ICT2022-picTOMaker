package kr.co.picTO.s3.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.application.ResponseLoggingService;
import kr.co.picTO.s3.application.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"5. File Upload Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/picTO")
public class FileUploadController {
    private final String ClassName = this.getClass().getName();
    private final FileUploadService fileUploadService;
    private final ResponseLoggingService loggingService;

    @PostMapping(value = "/register/{email}/{provider}")
    public ResponseEntity<?> uploadFile(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        loggingService.httpPathStrLoggingWithRequest(ClassName, "uploadFile", octet.get("image"), email, provider);
        return ResponseEntity.ok().body(fileUploadService.uploadImage(fileUploadService.decodeAndConvertFile(octet.get("image")), email, provider));
    }

    @PutMapping(value = "/update/{email}/{id}")
    public ResponseEntity<?> updatePicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable Long id) {
        loggingService.httpPathStrLoggingWithRequest(ClassName, "updatePicTo", octet.get("octet"), email, String.valueOf(id));
        return ResponseEntity.ok().body(fileUploadService.updatePicToByEmailAndId(fileUploadService.decodeAndConvertFile(octet.get("octet")), email, id));
    }

    @PostMapping(value = "/get/{email}/{provider}")
    public ResponseEntity<?> getPicTo(@PathVariable String email, @PathVariable String provider) {
        loggingService.httpPathStrLogging(ClassName, "getPicTo", email, provider, "");
        return ResponseEntity.ok().body(fileUploadService.getPicToByEmail(email, provider));
    }

    @PostMapping(value = "/count/{email}/{provider}")
    public ResponseEntity<?> getPicToCount(@PathVariable String email, @PathVariable String provider) {
        loggingService.httpPathStrLogging(ClassName, "getPicToCount", email, provider, "");
        return ResponseEntity.ok().body(fileUploadService.getPicToCountByEmailAndProvider(email, provider));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePicTo(@PathVariable Long id) {
        loggingService.httpPathStrLogging(ClassName, "deletePicTo", String.valueOf(id), "", "");
        return ResponseEntity.ok().body(fileUploadService.deletePicToById(id));
    }
}
