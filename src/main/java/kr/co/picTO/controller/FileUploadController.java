package kr.co.picTO.controller;

import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.s3.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/img")
@Log4j2
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final ResponseService responseService;

    @PostMapping(value = "/upload")
    public ResponseEntity<SingleResult<String>> uploadFile(@RequestPart MultipartFile multipartFile) {
        ResponseEntity<SingleResult<String>> ett = null;
        log.info("File Upload Controller uploadFile multipartFile : " + multipartFile.getName());
        try {
            String file = fileUploadService.uploadImage(multipartFile);
            log.info("File Upload Controller uploadFile file : " + file);

            SingleResult<String> result = responseService.getSingleResult(file);
            log.info("File Upload Controller uploadFile result getC : " + result.getCode());
            log.info("File Upload Controller uploadFile result getD : " + result.getData());
            log.info("File Upload Controller uploadFile result getM : " + result.getMsg());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller uploadFile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller uploadFile error occurred : " + e.getMessage());
        }
        log.info("File Upload Controller uploadFile file : " + multipartFile);
        return ett;
    }
}
