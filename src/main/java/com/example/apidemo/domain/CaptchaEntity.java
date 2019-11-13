package com.example.apidemo.domain;

public class CaptchaEntity
{
    public String getCaptcha()
    {
        return captcha;
    }

    public void setCaptcha(String captcha)
    {
        this.captcha = captcha;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    private String captcha;
    private String token;



}
