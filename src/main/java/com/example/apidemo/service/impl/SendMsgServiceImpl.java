package com.example.apidemo.service.impl;

import com.example.apidemo.dao.IPDao;
import com.example.apidemo.dao.NumberDao;
import com.example.apidemo.domain.Sender;
import com.example.apidemo.service.SendMsgService;
import com.example.apidemo.util.ApiResult;
import com.example.apidemo.util.CircularCounter;
import com.example.apidemo.util.IsMobile;
import com.example.apidemo.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class SendMsgServiceImpl implements SendMsgService
{
    @Autowired
    NumberDao numberDao;
    @Autowired
    IPDao ipDao;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    CircularCounter circularCounter=new CircularCounter();

    Logger logger = LoggerFactory.getLogger(getClass());
    public ApiResult  sendMsg(Sender sender)
    {
        ApiResult result=new ApiResult();
        String phonenumbers=sender.getPhonenumbers();
        String ipadress=sender.getIp();
       //先验证是否是合法手机号
        IsMobile ismobile=new IsMobile();
        if(!ismobile.IsMobile(phonenumbers))
        {
            logger.info(sender.toString()+"手机号码非法");
            return result.illegalfail();
        }
        //TODO IP黑名单

        //号码黑名单
        if(numberDao.isBlackNumber(phonenumbers))
        {
            logger.info(sender.toString()+"号码在黑名单中");
            return result.refusefail(ResultCode.MOBlILE_NUMBER_BLACK);
        }

        try
        {
            //取出该号码今日发送次数
            Integer phonetimes=numberDao.findByNumbers(phonenumbers);
            //取出该ip地址今日发送次数
            Integer iptimes=ipDao.findByIPAdress((ipadress));
            System.out.println("ip发送次数"+iptimes);
            if(phonetimes>3)
            {
                return result.refusefail(ResultCode.MOBILE_NUMBER_MC);
            }
            if(iptimes>5)
            {
                return result.refusefail(ResultCode.IP_ADRESS_MC);
            }
            else
            {
                numberDao.incrNumbers(phonenumbers);
                ipDao.incrIP(ipadress);
                logger.info("发送信息:"+sender.toString());
                Map map = new HashMap();
                map.put("phonenumbers",phonenumbers);
                map.put("msg",sender.getMsg());
                jmsMessagingTemplate.convertAndSend("tzy_msg", map);
                System.out.println("今日发送短信数量："+circularCounter.addAndGet());
                return result.success();
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            return result.refusefail(ResultCode.SERVICE_WRONG);
        }
    }
}
