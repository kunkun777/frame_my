package com.zxc.enums;

import com.zxc.enums.base.ResponseCodeInterface;
import lombok.AllArgsConstructor;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

@AllArgsConstructor
public enum BaseResponseCode implements ResponseCodeInterface {
    SUCCESS( 200,"请求成功"),
    CREATED(201,"创建成功"),
    DELETED(204,"删除成功"),
    BAD_REQUEST(400,"请求的地址不存在或者包含不支持的参数"),
    UNAUTHORIZED(401,"未授权"),
    FORBIDDEN(403,"被禁止访问"),
    NOT_FOUND(404,"请求的资源不存在"),
    UNPROCESABLE_ENTITY(422,"[POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误"),
    INTERNAL_SERVER_ERROR(500,"内部错误"),
    SYSTEM_EXCEPTION(501,"系统异常"),
    VALIDATOR_ERROR(402,"验证出错"),
    ACCOUNT_ERROR(423,"账号不存在,请注册"),
    ACCOUNT_LOCK(424,"账号已经被锁定,请联系平台管理员"),
    ACCOUNT_PASSWORD_ERROR(424,"密码不正确,请重新登录"),
    TOKEN_NOT_NULL(4123,"用户未登录，请登录"),
    TOKEN_ERROR(400123,"token认证失败，请重新认证"),
    ;

    int code;
    String msg;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
