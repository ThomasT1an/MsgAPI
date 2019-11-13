package com.example.apidemo.exception;

import com.example.apidemo.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import static org.springframework.http.HttpStatus.NOT_EXTENDED;

/**
 * Created by mazhq on 2018/7/10
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("错误");
        return new ResponseEntity<Object>("发生错误", NOT_EXTENDED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String jsonHandler(HttpServletRequest request, Exception e) {
        logger.error("请求路径："+request.getRequestURL().toString()+"; 发生错误！");
        log(e, request);
        return "请求路径："+request.getRequestURL().toString()+"; 发生错误！";
    }

    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");

        logger.error("异常信息：", ex);
        logger.error("请求地址：" + request.getRequestURL());
        Enumeration enumeration = request.getParameterNames();
        logger.error("请求参数");
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            logger.error(name + "---" + request.getParameter(name));
        }

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }
        logger.error("************************异常结束*******************************");
    }
}
