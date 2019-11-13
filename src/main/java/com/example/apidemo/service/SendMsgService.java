package com.example.apidemo.service;

import com.example.apidemo.domain.Sender;
import com.example.apidemo.util.ApiResult;

public interface SendMsgService
{
    ApiResult sendMsg(Sender sender);
}
