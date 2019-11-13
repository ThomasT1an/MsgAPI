package com.example.apidemo.service.impl;

import com.example.apidemo.dao.KaptchaDao;
import com.example.apidemo.service.KaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class KaptchaServiceImpl implements KaptchaService
{
    @Autowired
    DefaultKaptcha producer;
    @Autowired
    KaptchaDao kaptchaDao;

    @Override
    public Map<String, Object> generateVerificationCode() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        // 生成文字验证码
        String text = producer.createText();
        System.out.println("Kaptcha文字验证码是："+text);
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(text);
        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        map.put("img", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        //生成验证码对应的token  以token为key  验证码为value存在redis中
        String codeToken = UUID.randomUUID().toString();
        System.out.println("Kaptcha的Token是："+codeToken);
        kaptchaDao.createVer(codeToken,text);
        map.put("cToken", codeToken);
        return map;
    }

    @Override
    public String checkCodeToken(String sToken, String textStr)
    {
        Object value = kaptchaDao.findVerCode(sToken);
        if(value==null)
        {

            return "Invalid";
        }
        if (value != null && textStr.equals(value)) {
            deleteVerCode(sToken);
            return "Success";
        } else {
            deleteVerCode(sToken);
            return "WrongAns";
        }
    }
    @Override
    public void deleteVerCode(String token)
    {
        kaptchaDao.deleteVer(token);
    }
}
