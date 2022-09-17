package kr.co.picto.file.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.file.application.FileUploadService;
import kr.co.picto.file.dto.S3ImageResponseDto;
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
    @GetMapping(value = "/{provider}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<ListResult<S3ImageResponseDto>> getPicTo(@RequestHeader(value = "X-AUTH-TOKEN") String access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToByEmail(access_token, provider));
    }

    /**
     * frontend - MyPage-profile.js
     **/
    @GetMapping(value = "/count/{provider}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<SingleResult<Long>> getPicToCount(@RequestHeader(value = "X-AUTH-TOKEN") String access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToCountByEmailAndProvider(access_token, provider));
    }

    /**
     * frontend - EditTool.js
     **/
    @PostMapping(value = "/{provider}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<SingleResult<Long>> uploadPicTo(@RequestBody Map<String, String> octet, @RequestHeader(value = "X-AUTH-TOKEN") String access_token, @PathVariable String provider) {
        return ResponseEntity.ok().body(fileUploadService.uploadImage(fileUploadService.decodeAndConvertFile(octet.get("image")), access_token, provider));
    }

    /**
     * frontend - Posts.js
     **/
    @PutMapping(value = "/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<SingleResult<Integer>> updatePicTo(@RequestBody Map<String, String> octet, @RequestHeader(value = "X-AUTH-TOKEN") String access_token, @PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.updatePicToByEmailAndId(fileUploadService.decodeAndConvertFile(octet.get("octet")), access_token, id));
    }

    /**
     * frontend - Posts.js
     **/
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SingleResult<Integer>> deletePicTo(@PathVariable Long id) {
        return ResponseEntity.ok().body(fileUploadService.deletePicToById(id));
    }
}
