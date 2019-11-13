package com.example.apidemo.service.impl;

import com.example.apidemo.dao.NumberDao;
import com.example.apidemo.service.BlackNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackNumberServiceImpl implements BlackNumberService
{
    @Autowired
    NumberDao numberDao;
    Logger logger = LoggerFactory.getLogger(getClass());

    public void insertBlackNumber(String phonenumbers)
    {
        logger.info((phonenumbers+"加入黑名单"));
        numberDao.insertBlack(phonenumbers);
    }
}
