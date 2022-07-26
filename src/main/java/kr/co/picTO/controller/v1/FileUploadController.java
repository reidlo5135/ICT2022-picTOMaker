package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import kr.co.picTO.entity.s3.BaseS3Image;
import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.response.ResponseLoggingService;
import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.s3.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Api(tags = {"5. File Upload Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/picTO")
public class FileUploadController {

    private static final String className = FileUploadController.class.toString();

    private final FileUploadService fileUploadService;
    private final ResponseService responseService;
    private final ResponseLoggingService loggingService;

    @PostMapping(value = "/register/{email}/{provider}")
    public ResponseEntity<SingleResult<String>> uploadFile(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        ResponseEntity<SingleResult<String>> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "uploadFile", octet.get("image"), email, provider);

        try {
            MultipartFile multipartFile = fileUploadService.decodeAndConvertFile(octet.get("image"));
            log.info("File Upload Controller uploadFile multipartFile : " + multipartFile);

            String file = fileUploadService.uploadImage(multipartFile, email, provider);
            log.info("File Upload Controller uploadFile file : " + file);

            SingleResult<String> result = responseService.getSingleResult(file);
            loggingService.singleResultLogging(className, "uploadFile", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller uploadFile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller uploadFile error occurred : " + e.getMessage());
        }
        return ett;
    }

    @PutMapping(value = "/update/{email}/{id}")
    public ResponseEntity<SingleResult<Integer>> updatePicTo(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable Long id) {
        ResponseEntity<SingleResult<Integer>> ett = null;
        loggingService.httpPathStrLoggingWithRequest(className, "updatePicTo", octet.get("octet"), email, String.valueOf(id));
        try {
            MultipartFile multipartFile = fileUploadService.decodeAndConvertFile(octet.get("octet"));
            log.info("File Upload Controller updatePicTo multipartFile : " + multipartFile);

            Integer updateId = fileUploadService.updatePicToByEmailAndId(multipartFile, email, id);
            log.info("File Upload Controller updatePicTo updateId : ", updateId);

            SingleResult<Integer> result = responseService.getSingleResult(updateId);
            loggingService.singleResultLogging(className, "updatePicTo", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller uploadFile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller updatePicTo error occurred : " + e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/get/{email}/{provider}")
    public ResponseEntity<ListResult<BaseS3Image>> getPicTo(@PathVariable String email, @PathVariable String provider) {
        ResponseEntity<ListResult<BaseS3Image>> ett = null;
        loggingService.httpPathStrLogging(className, "getPicTo", email, provider, "");

        try {
            List<BaseS3Image> list = fileUploadService.getPicToByEmail(email, provider);
            log.info("File Upload Controller getPicTo list : " + list);

            ListResult<BaseS3Image> result = responseService.getListResult(list);
            loggingService.listResultLogging(className, "getPicTo", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller uploadFile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller getPicTo error occurred : " + e.getMessage());
        }
        return ett;
    }

    @PostMapping(value = "/count/{email}/{provider}")
    public ResponseEntity<SingleResult<Long>> getPicToCount(@PathVariable String email, @PathVariable String provider) {
        ResponseEntity<SingleResult<Long>> ett = null;
        loggingService.httpPathStrLogging(className, "getPicToCount", email, provider, "");
        try {
            Long count = fileUploadService.getPicToCountByEmailAndProvider(email, provider);
            log.info("File Upload Controller getPicToCount result : ", count);

            SingleResult<Long> result = responseService.getSingleResult(count);
            loggingService.singleResultLogging(className, "getPicToCount", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller uploadFile ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller getPicToCount error occurred : " + e.getMessage());
        }
        return ett;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<SingleResult<Integer>> deletePicTo(@PathVariable Long id) {
        ResponseEntity<SingleResult<Integer>> ett = null;
        loggingService.httpPathStrLogging(className, "deletePicTo", String.valueOf(id), "", "");
        try {
            Integer deleteId = fileUploadService.deletePicToById(id);
            log.info("File Upload Controller deletePicTo result : ", deleteId);

            SingleResult<Integer> result = responseService.getSingleResult(deleteId);
            loggingService.singleResultLogging(className, "deletePicTo", result);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            ett = new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
            log.info("File Upload Controller deletePicTo ett : " + ett);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("File Upload Controller deletePicTo error occurred : " + e.getMessage());
        }
        return ett;
    }
}
