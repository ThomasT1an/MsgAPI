package com.example.apidemo.controller;

import com.example.apidemo.domain.Sender;
import com.example.apidemo.service.BlackNumberService;
import com.example.apidemo.service.SendMsgService;
import com.example.apidemo.util.ApiResult;
import com.example.apidemo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.apidemo.util.IsMobile;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class SendMsgController
{

    @Autowired
    private SendMsgService sendMsgService;
    @Autowired
    private BlackNumberService blackNumberService;

    @PostMapping(path="/POST/send_msg")
    public ApiResult test(@RequestBody Sender sender, HttpServletRequest request)
    {
        ApiResult result=new ApiResult();

        String realip=IpUtil.getIpAddr(request);
        //System.out.println("realip:"+realip);
        result=sendMsgService.sendMsg(sender);
        return result;
    }

    @PostMapping(path="/POST/black_number")
    public ApiResult blacknumber(String numbers)
    {
        ApiResult result=new ApiResult().success();
        blackNumberService.insertBlackNumber(numbers);
        return result;
    }
}
