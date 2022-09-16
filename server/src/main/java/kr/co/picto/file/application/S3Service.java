package kr.co.picto.file.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.co.picto.common.configuration.S3Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3Service implements UploadService{

    private final AmazonS3 amazonS3;
    private final S3Component s3Component;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata));
    }

    @Override
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(s3Component.getBucket(), fileName).toString();
    }
}
