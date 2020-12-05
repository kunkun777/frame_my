package com.zxc.exception;

import com.zxc.enums.BaseResponseCode;

public class BusinessException extends RuntimeException{
    /**
     * 提示编码
     */
    private final int messageCode;
    /**
     * 提示信息
     */
    private final String messageDefault;

    public BusinessException(int messageCode, String messageDefault) {
        this.messageCode = messageCode;
        this.messageDefault = messageDefault;
    }
    public BusinessException(BaseResponseCode baseResponseCode) {
        this.messageCode = baseResponseCode.getCode();
        this.messageDefault = baseResponseCode.getMessage();
    }
    public int getMessageCode() {
        return messageCode;
    }

    public String getMessage() {
        return messageDefault;
    }

}
