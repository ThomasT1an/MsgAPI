package com.example.apidemo.controller;

import com.example.apidemo.service.KaptchaService;
import com.example.apidemo.util.ApiResult;
import com.example.apidemo.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class KaptchaController
{
    @Autowired
    KaptchaService kaptchaService;

    @ResponseBody
    @RequestMapping("/GETS/kaptcha")
    public Map<String, Object> captcha(HttpServletResponse response) throws Exception
    {
        return kaptchaService.generateVerificationCode();
    }

    @RequestMapping("/POST/checkkaptcha")
    @ResponseBody
    public ApiResult check(@RequestParam("cToken") String cToken, @RequestParam("captcha") String captcha)
    {
        String res=kaptchaService.checkCodeToken(cToken,captcha);
        switch(res)
        {
            case "Invalid":
            return new ApiResult().worngver(ResultCode.INVALID_VERCODE);
            case "WrongAns":
            return new ApiResult().worngver(ResultCode.WRONG_VERCODE);
            case "Success":
            return new ApiResult().success();
        }
        return new ApiResult().worngver(ResultCode.SERVICE_WRONG);
    }
}