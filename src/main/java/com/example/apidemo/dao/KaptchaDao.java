package com.example.apidemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class KaptchaDao
{
    @Autowired
    private StringRedisTemplate strRedis;
    @Value("${timeout.vercode}")
    private int timeout_vercode;

    public String findVerCode(String token)
    {
        String vercode=strRedis.opsForValue().get(token);
        return vercode;

    }

    public void createVer(String token,String vercode)
    {
        strRedis.opsForValue().set(token, vercode,timeout_vercode, TimeUnit.SECONDS);
    }

    public void deleteVer(String token)
    {
        strRedis.delete(token);
    }
}
