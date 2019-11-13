package com.example.apidemo.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Consumer
{
    @JmsListener(destination="tzy_msg")
    public void receiveMessage(Map map) { //
        System.out.println(" 消费者发送消息：  " + map);
    }
}

