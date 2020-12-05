package com.zxc.from.vo;

import com.zxc.enums.base.ResponseCodeInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataResult<T> {
    /**
     * 返回状态码
     */
    @ApiModelProperty("状态码")
    private int code;
    /**
     * 返回信息
     */
    @ApiModelProperty("提示信息")
    private String msg;
    /**
     * 返回的数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    private DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private DataResult(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public static <T> DataResult getResult(int code, String msg,T data ){
        return new DataResult(code,msg,data);
    }
    public static <T> DataResult getResult(int code, T data ){
        return new DataResult(code,data);
    }
    public static <T> DataResult getResult(int code, String msg ){
        return new DataResult(code,msg);
    }
    public static <T> DataResult getResult(ResponseCodeInterface responseCodeInterface ){
        return new DataResult(responseCodeInterface);
    }
    public static <T> DataResult getResult(ResponseCodeInterface responseCodeInterface ,T data){
        return new DataResult(responseCodeInterface,data);
    }


    private DataResult(ResponseCodeInterface responseCodeInterface){
        this.code=responseCodeInterface.getCode();
        this.msg=responseCodeInterface.getMessage();
        this.data=null;
    }
    private DataResult(ResponseCodeInterface responseCodeInterface, T data){
        this.code=responseCodeInterface.getCode();
        this.msg=responseCodeInterface.getMessage();
        this.data=data;
    }

}
