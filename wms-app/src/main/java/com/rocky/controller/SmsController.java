package com.rocky.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.rocky.exception.Bizexception;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Resource(name = "redisTemplate")
    private ValueOperations<String,String> valueOperations;

    @GetMapping("/email")
    public String sendEmail(@RequestParam String email) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.aliyun.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("61813631@aliyun.com");
        account.setUser("61813631@aliyun.com");
        account.setPass("ChenMing125..");
        int vcode = RandomUtil.randomInt(1000, 9999);
        valueOperations.set("sms."+email, vcode+"", Duration.ofMinutes(10));
        MailUtil.send(account, CollUtil.newArrayList(email), "验证码", "您的验证码是：" + vcode, false);
        return "ok";
    }

    @GetMapping("/phone")
    public String sendPhone(@RequestParam String tel) {

        //保证同一个手机号一天最多发送5条短信
        if(valueOperations.get("sms."+tel)!= null){
            return "发送过于频繁";
        }
        if(StrUtil.isBlank(tel)){
            return "手机号不能为空";
        }
        if(!tel.matches("^1[3|4|5|7|8][0-9]\\d{8}$")){
            return "手机号格式不正确";
        }
        if(tel.length()!= 11){
            return "手机号长度不正确";
        }

        //3：验证一个手机号一天只能发送五次验证码
        if (valueOperations.get("sms." + tel)!= null){
            int count = Integer.parseInt(valueOperations.get("sms." + tel));
            if (count >= 5){
                throw new Bizexception(403,"一天只能发送五次验证码");
            }
        }


        //省略了手机号的验证
        String url = "https://dfsns.market.alicloudapi.com/data/send_sms";
        int vcode = RandomUtil.randomInt(1000, 9999);
        String body = StrUtil.format("content=code:{}&template_id=TPL_0000&phone_number={}", vcode, tel);
        HttpResponse response = HttpRequest
                .of(url)
                .method(Method.POST)
                .header("Authorization", "APPCODE 363efa0e07bb4c368b05f500db153ec5")
                .body(body).execute();
        if(response.getStatus() == 200){
            valueOperations.set("sms."+tel, vcode+"", Duration.ofMinutes(10));
            return "ok";
        }
        return "发送失败";
    }
}
