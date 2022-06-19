package kr.co.picTO.service.response;

import kr.co.picTO.model.response.ListResult;
import kr.co.picTO.model.response.SingleResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ResponseLoggingService {

    public void singleResultLogging(String className, String methodName, SingleResult result) {
       try {
           log.info("Response Logging SVC singleResult logging ClassName : " + className);
           log.info("Response Logging SVC singleResult logging MethodName : " + methodName);
           log.info(className + " " + methodName + " result getCode : " + result.getCode());
           log.info(className + " " + methodName + " result getData : " + result.getData());
           log.info(className + " " + methodName + " result getMSg : " + result.getMsg());
       } catch (Exception e) {
           e.printStackTrace();
           log.error("Response Logging SVC singleResult logging error occurred : " + e.getMessage());
       }
    }

    public void listResultLogging(String className, String methodName, ListResult result) {
        try {
            log.info("Response Logging SVC listResult logging ClassName : " + className);
            log.info("Response Logging SVC listResult logging MethodName : " + methodName);
            log.info(className + " " + methodName + " result getCode : " + result.getCode());
            log.info(className + " " + methodName + " result getData : " + result.getList());
            log.info(className + " " + methodName + " result getMSg : " + result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC listResult logging error occurred : " + e.getMessage());
        }
    }
}
