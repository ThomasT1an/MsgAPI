package com.example.apidemo.util;

import java.util.ArrayList;
import java.util.List;

public enum ResultCode
{
    /*成功状态码*/
    SUCCESS(1,"成功"),

    /*参数错误*/
    MOBILE_NUMBER_ILLEGAL(10000,"手机号不规范"),
    WRONG_VERCODE(10001,"验证码输入错误"),
    INVALID_VERCODE(10002,"验证码失效"),

    /*拒绝服务*/
    MOBILE_NUMBER_MC(20001,"该号码发送次数过多"),
    IP_ADRESS_MC(20002,"该IP发送次数过多"),
    MOBlILE_NUMBER_BLACK(20003,"该号码加入黑名单"),
    IP_ADRESS_BLACK(20004,"该IP地址加入黑名单"),

    /*服务器错误*/
    SERVICE_WRONG(50001,"服务器异常");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

    //校验重复的code值
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code());
            }
        }
    }
}
