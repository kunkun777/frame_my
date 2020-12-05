package com.zxc.exception.handler;

import com.zxc.enums.BaseResponseCode;
import com.zxc.exception.BusinessException;
import com.zxc.from.vo.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

/**
 * @target 全局异常处理
 * @Auhtor zxc
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    /**
     * 抛出响应的异常都交给这个类
     */
    @ExceptionHandler(Exception.class)
    public DataResult handleException(Exception e) {
        log.error("handlerException...，系统错误", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_EXCEPTION);
    }

    @ExceptionHandler(BusinessException.class)
    public DataResult handlerBusinessException(BusinessException e) {
        log.error("BusinessException...，流程错误", e);
        return DataResult.getResult(e.getMessageCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException{}，对不起，身份密码不匹配，请重试！", e);
        return DataResult.getResult(4001, e.getBindingResult().getAllErrors());
    }
}
