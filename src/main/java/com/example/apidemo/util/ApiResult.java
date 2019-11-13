package com.example.apidemo.util;

import java.io.Serializable;
public class ApiResult implements Serializable
{
    private static final long serialVersionUID=-3948389232146368059L;

    private Integer code;

    /** 提示信息. */
    private String msg;

    public void setResultCode(ResultCode code)
    {
        this.code = code.code();
        this.msg = code.message();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ApiResult(){}

    public ApiResult success()
    {
        ApiResult result=new ApiResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    public ApiResult illegalfail()
    {
        ApiResult result=new ApiResult();
        result.setResultCode(ResultCode.MOBILE_NUMBER_ILLEGAL);
        return result;
    }
    public ApiResult refusefail(ResultCode resultCode)
    {
        ApiResult result=new ApiResult();
        result.setResultCode(resultCode);
        return result;
    }
    public ApiResult worngver(ResultCode resultCode)
    {
        ApiResult result=new ApiResult();
        result.setResultCode(resultCode);
        return result;
    }
    public ApiResult commfailure(int code,String msg)
    {
        ApiResult result=new ApiResult();
        this.code=code;
        this.msg=msg;
        return result;
    }
}

