package kr.co.picTO.controller.v1;

import io.swagger.annotations.Api;
import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;
import kr.co.picTO.service.response.ResponseService;
import kr.co.picTO.service.s3.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = {"5. File Upload Controller"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/upload")
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final ResponseService responseService;

    @PostMapping(value = "/register/{email}/{provider}")
    public ResponseEntity<SingleResult<String>> uploadFile(@RequestBody Map<String, String> octet, @PathVariable String email, @PathVariable String provider) {
        ResponseEntity<SingleResult<String>> ett = null;
        log.info("File Upload Controller uploadFile octet : " + octet);
        log.info("File Upload Controller uploadFile email : " + email);
        log.info("File Upload Controller uploadFile provider : " + provider);

        String[] strings = octet.get("image").split(",");
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
        try {
            byte[] decoded = DatatypeConverter.parseBase64Binary(strings[1]);
            log.info("File Upload Controller decoded : " + decoded);

            Date nowTime = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            String formatTime = simpleDateFormat.format(nowTime);

            String path = "C:\\myPicTO\\" + formatTime;
            File folder = new File(path);
            if(!folder.exists()) {
               if(folder.mkdirs()) {
                   log.info("File Upload Controller folder : "+ folder + " are generated");
               }
            }

            String fileName = decoded + "." + extension;
            File newFile = new File(path, fileName);

            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile))) {
                log.info("File Upload Controller newFile : " + newFile);
                outputStream.write(decoded);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileItem fileItem = new DiskFileItem("file", Files.probeContentType(newFile.toPath()), false, newFile.getName(), (int)newFile.length(), newFile.getParentFile());
            InputStream input = new FileInputStream(newFile);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);

            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            log.info("File Upload Controller multipartFile : " + multipartFile);

            String file = fileUploadService.uploadImage(multipartFile, email, provider);
            log.info("File Upload Controller uploadFile file : " + file);

            SingleResult<String> result = responseService.getSingleResult(file);
            log.info("File Upload Controller uploadFile result getC : " + result.getCode());
            log.info("File Upload Controller uploadFile result getD : " + result.getData());
            log.info("File Upload Controller uploadFile result getM : " + result.getMsg());

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

    @PostMapping(value = "/get/picTO/{email}/{provider}")
    public ResponseEntity<ListResult<String>> getPicTo(@PathVariable String email, @PathVariable String provider) {
        ResponseEntity<ListResult<String>> ett = null;
        log.info("File Upload Controller getPicTo email : " + email);
        log.info("File Upload Controller getPicTo provider : " + provider);
        try {
            List<String> list = fileUploadService.getPicToByEmail(email, provider);
            log.info("File Upload Controller getPicTo list : " + list);

            ListResult<String> result = responseService.getListResult(list);
            log.info("File Upload Controller getPicTo result getC : " + result.getCode());
            log.info("File Upload Controller getPicTo result getL : " + result.getList());
            log.info("File Upload Controller getPicTo result getM : " + result.getMsg());

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
}
