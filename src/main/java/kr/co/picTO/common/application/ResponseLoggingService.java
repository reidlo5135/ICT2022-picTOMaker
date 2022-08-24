package kr.co.picTO.common.application;

import kr.co.picTO.common.domain.CommonResult;
import kr.co.picTO.common.domain.ListResult;
import kr.co.picTO.common.domain.SingleResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ResponseLoggingService {

    public void singleResultLogging(Class paramClass, String methodName, SingleResult result) {
        try {
            log.info("Response Logging SVC singleResult logging ClassName : " + paramClass.getName());
            log.info("Response Logging SVC singleResult logging MethodName : " + methodName);
            log.info(paramClass.getName() + " " + methodName + " result getCode : " + result.getCode());
            log.info(paramClass.getName() + " " + methodName + " result getData : " + result.getData());
            log.info(paramClass.getName() + " " + methodName + " result getMSg : " + result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC singleResult logging error occurred : " + e.getMessage());
        }
    }

    public void listResultLogging(Class paramClass, String methodName, ListResult result) {
        try {
            log.info("Response Logging SVC listResult logging ClassName : " + paramClass.getName());
            log.info("Response Logging SVC listResult logging MethodName : " + methodName);
            log.info(paramClass.getName() + " " + methodName + " result getCode : " + result.getCode());
            log.info(paramClass.getName() + " " + methodName + " result getList : " + result.getList());
            log.info(paramClass.getName() + " " + methodName + " result getMSg : " + result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC listResult logging error occurred : " + e.getMessage());
        }
    }

    public void commonResultLogging(Class paramClass, String methodName, CommonResult result) {
        try {
            log.error("Response Logging SVC commonResult logging ClassName : " + paramClass.getName());
            log.error("Response Logging SVC commonResult logging MethodName : " + methodName);
            log.error(paramClass.getName() + " " + methodName + " result getCode : " + result.getCode());
            log.error(paramClass.getName() + " " + methodName + " result isSuccess : " + result.isSuccess());
            log.error(paramClass.getName() + " " + methodName + " result getMSg : " + result.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC commonResult logging error occurred : " + e.getMessage());
        }
    }

    public void httpPathStrLogging(String className, String methodName, String str, String str2, String str3) {
        try {
            log.info("Response Logging SVC httpPathStrLogging ClassName : " + className);
            log.info("Response Logging SVC httpPathStrLogging MethodName : " + methodName);
            log.info("Response Logging SVC httpPathStrLogging logging str : " + str);
            log.info("Response Logging SVC httpPathStrLogging logging str2 : " + str2);
            log.info("Response Logging SVC httpPathStrLogging logging str3 : " + str3);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC httpPathStrLogging logging error occurred : " + e.getMessage());
        }
    }

    public void httpPathStrLoggingWithRequest(String className, String methodName, String request, String str, String str2) {
        try {
            log.info("Response Logging SVC httpPathStrLoggingWithRequest logging ClassName : " + className);
            log.info("Response Logging SVC httpPathStrLoggingWithRequest logging MethodName : " + methodName);
            log.info("Response Logging SVC httpPathStrLoggingWithRequest logging requestMap : " + request);
            log.info("Response Logging SVC httpPathStrLoggingWithRequest logging str : " + str);
            log.info("Response Logging SVC httpPathStrLoggingWithRequest logging str2 : " + str2);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Response Logging SVC httpPathStrLoggingWithRequest logging error occurred : " + e.getMessage());
        }
    }
}
