package com.example.apidemo.service;

import java.util.Map;

public interface KaptchaService
{
    //生成验证码
    Map<String, Object> generateVerificationCode() throws Exception;

    //验证验证码
    String checkCodeToken(String sToken, String textStr);

    //删除验证码
    void deleteVerCode(String token);
}
