package kr.co.picto.socket.presentation;

import kr.co.picto.common.domain.ListResult;
import kr.co.picto.common.domain.SingleResult;
import kr.co.picto.file.application.FileUploadService;
import kr.co.picto.file.dto.S3ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@MessageMapping(value = "/picto/")
public class WSFileUploadController {
    private final FileUploadService fileUploadService;

    /**
     * frontend - MyPagePicToDetails.js
     **/
    @SendTo(value = "/sub/picto/find")
    @MessageMapping(value = "/find/{provider}")
    public ResponseEntity<ListResult<S3ImageResponseDto>> getPicTo(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToByEmail(token, provider));
    }

    /**
     * frontend - MyPageProfileDetails.js
     **/
    @SendTo(value = "/sub/picto/count")
    @MessageMapping(value = "/count/{provider}")
    public ResponseEntity<SingleResult<Long>> getPicToCount(@Header(value = "X-AUTH-TOKEN") String token, @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(fileUploadService.getPicToCountByEmailAndProvider(token, provider));
    }

    /**
     * frontend - EditTool.js
     **/
    @SendTo(value = "/sub/picto/register")
    @MessageMapping(value = "/register/{provider}")
    public ResponseEntity<SingleResult<Long>> uploadPicTo(
            Map<String, String> octet,
            @Header(value = "X-AUTH-TOKEN") String token,
            @DestinationVariable(value = "provider") String provider) {
        return ResponseEntity.ok().body(fileUploadService.uploadImage(fileUploadService.decodeAndConvertFile(octet.get("image")), token, provider));
    }

    /**
     * frontend - MyPagePicToPosting.js
     **/
    @SendTo(value = "/sub/picto/up")
    @MessageMapping(value = "/up/{id}")
    public ResponseEntity<SingleResult<Integer>> updatePicTo(
            Map<String, String> octet,
            @Header(value = "X-AUTH-TOKEN") String token,
            @DestinationVariable(value = "id") long id) {
        return ResponseEntity.ok().body(fileUploadService.updatePicToByEmailAndId(fileUploadService.decodeAndConvertFile(octet.get("octet")), token, id));
    }

    /**
     * frontend - MyPagePicToPosting.js
     **/
    @SendTo(value = "/sub/picto/drop")
    @MessageMapping(value = "/drop/{id}")
    public ResponseEntity<SingleResult<Integer>> deletePicTo(@DestinationVariable(value = "id") long id) {
        return ResponseEntity.ok().body(fileUploadService.deletePicToById(id));
    }
}
