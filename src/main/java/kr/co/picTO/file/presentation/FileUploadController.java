package kr.co.picTO.file.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.file.application.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"5. File Upload Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/picto")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping(value = "/register/email/{email}/provider/{provider}")
    public ResponseEntity<?> uploadPicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.uploadImage(fileUploadService.decodeAndConvertFile(octet.get("image")), email, provider));
    }

    @GetMapping(value = "/find/email/{email}/provider/{provider}")
    public ResponseEntity<?> getPicTo(@PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToByEmail(email, provider));
    }

    @GetMapping(value = "/count/email/{email}/provider/{provider}")
    public ResponseEntity<?> getPicToCount(@PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToCountByEmailAndProvider(email, provider));
    }

    @PutMapping(value = "/email/{email}/id/{id}")
    public ResponseEntity<?> updatePicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.updatePicToByEmailAndId(fileUploadService.decodeAndConvertFile(octet.get("octet")), email, id));
    }

    @DeleteMapping(value = "/id/{id}")
    public ResponseEntity<?> deletePicTo(@PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.deletePicToById(id));
    }
}
