package com.njit.zang.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Administrator on 2019/4/11.
 */
@Data
@Component
public class Mail {
    @Autowired
    private JavaMailSender mailSender;

    private final static String SEVER_MAIL = "17712850774@163.com";

    public String sendMessage(String toMail){
        String captcha = this.randomNum();
        String text = "尊敬的用户您好，你本次的验证码为"+captcha+",请尽快使用，防止验证码失效！";
        System.out.println(text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SEVER_MAIL);
        message.setTo(toMail);
        message.setSubject("\"抽象之间\"的验证码");
        message.setText(text);

        mailSender.send(message);
        return captcha;
    }

    public String randomNum(){
        Random random = new Random();
        String num ="";
        int a = 6;
        for(int i = 0;i<a;i++){
            num += random.nextInt(9)+1;
        }
        return num;
    }
}
