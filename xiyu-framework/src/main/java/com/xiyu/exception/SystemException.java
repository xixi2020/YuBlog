package com.xiyu.exception;

import com.xiyu.enms.AppHttpCodeEnum;

/**
 * 统一异常处理，异常中的信息封装成ResponseResult响应给前端
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
