package kr.co.picTO.file.presentation;

import io.swagger.annotations.Api;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import kr.co.picTO.file.application.FileUploadService;
import kr.co.picTO.file.dto.S3ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author reidlo
 * 2022-09-06
 * ver 1.1.1
 **/
@Api(tags = {"5. File Upload Controller"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/picto")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    /**
     * frontend - MyPage-Mypic.js
     **/
    @GetMapping(value = "/{email}/{provider}")
    public ResponseEntity<ListResult<S3ImageResponseDto>> getPicTo(@PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToByEmail(email, provider));
    }

    /**
     * frontend - MyPage-profile.js
     **/
    @GetMapping(value = "/count/{email}/{provider}")
    public ResponseEntity<SingleResult<Long>> getPicToCount(@PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToCountByEmailAndProvider(email, provider));
    }

    /**
     * frontend - EditTool.js
     **/
    @PostMapping(value = "/{email}/{provider}")
    public ResponseEntity<SingleResult<Long>> uploadPicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.uploadImage(fileUploadService.decodeAndConvertFile(octet.get("image")), email, provider));
    }

    /**
     * frontend - Posts.js
     **/
    @PutMapping(value = "/{email}/{id}")
    public ResponseEntity<SingleResult<Integer>> updatePicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.updatePicToByEmailAndId(fileUploadService.decodeAndConvertFile(octet.get("octet")), email, id));
    }

    /**
     * frontend - Posts.js
     **/
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SingleResult<Integer>> deletePicTo(@PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.deletePicToById(id));
    }
}
