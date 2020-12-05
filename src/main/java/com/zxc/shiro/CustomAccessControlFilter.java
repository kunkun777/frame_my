package com.zxc.shiro;

import com.alibaba.fastjson.JSON;
import com.zxc.contants.Constant;
import com.zxc.enums.BaseResponseCode;
import com.zxc.exception.BusinessException;
import com.zxc.from.vo.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        log.info("----->request 请求地址：{}",request.getRequestURI());
        log.info("------>request 请求处理方式：{}",request.getMethod());
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        try {
            if (StringUtils.isEmpty(accessToken)) {
                throw new BusinessException(BaseResponseCode.TOKEN_NOT_NULL);
            }
            CustomUsernameAndPasswordToken customUsernameAndPasswordToken = new CustomUsernameAndPasswordToken(accessToken);
            getSubject(servletRequest, servletResponse).login(customUsernameAndPasswordToken);
        }catch (BusinessException e){
            customResponse(servletResponse,e.getMessageCode(),e.getMessage());
            return false;
        } catch (AuthenticationException e){
            if (e.getCause() instanceof BusinessException){
                BusinessException exception=(BusinessException) e.getCause();
                customResponse(servletResponse,exception.getMessageCode(),exception.getMessage());
            }else {
                customResponse(servletResponse,BaseResponseCode.TOKEN_ERROR.getCode(),BaseResponseCode.TOKEN_ERROR.getMessage());
            }
            return false;
        } catch (Exception e){
            customResponse(servletResponse,BaseResponseCode.SYSTEM_EXCEPTION.getCode(),BaseResponseCode.SYSTEM_EXCEPTION.getMessage());
            return false;
        }
        return true;
    }

    private void customResponse(ServletResponse response,int code,String msg){
        try {
            DataResult result = DataResult.getResult(code, msg);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setContentType("UTF-8");
            String str = JSON.toJSONString(result);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(str.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }catch (IOException e){
           log.error("customResponse...error");
        }
    }

}
