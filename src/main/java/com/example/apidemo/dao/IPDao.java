package com.example.apidemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class IPDao
{
    @Autowired
    private StringRedisTemplate strRedis;

    @Value("${timeout.IPaddress}")
    private  int timeout_IP;

    public Integer findByIPAdress(String IPAdress)
    {
        String times=(String)strRedis.opsForValue().get(IPAdress);
       // System.out.println(times);
        if(times==null)
        {
            createIP(IPAdress);
            return 1;
        }
        return Integer.parseInt(times);
    }

    public void createIP(String IPAdress)
    {
        strRedis.opsForValue().set(IPAdress, "1",timeout_IP, TimeUnit.SECONDS);
    }

    public void incrIP(String IPAdress)
    {
        strRedis.opsForValue().increment(IPAdress);
    }

}
