package com.example.apidemo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class NumberDao
{
    @Autowired
    private StringRedisTemplate strRedis;
    @Value("${timeout.numbers}")
    private int timeout_numbers;

    public Integer findByNumbers(String phonenumbers)
    {
        String times=(String)strRedis.opsForValue().get(phonenumbers);
        System.out.println(times);
        if(times==null)
        {
            createNumbers(phonenumbers);
            return 1;
        }
        return Integer.parseInt(times);
    }

    public void createNumbers(String phonenumbers)
    {
        strRedis.opsForValue().set(phonenumbers, "1",timeout_numbers, TimeUnit.SECONDS);
    }

    public void incrNumbers(String phonenumbers)
    {
        strRedis.opsForValue().increment(phonenumbers);
    }

    public void insertBlack(String value)
    {
        strRedis.opsForSet().add("blacknumbers",value);
    }

    public boolean isBlackNumber(String value)
    {
        return strRedis.opsForSet().isMember("blacknumbers",value);
    }
}
