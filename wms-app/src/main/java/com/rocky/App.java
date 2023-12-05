package com.rocky;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
//开启事务的支持
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
public class App {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        SpringApplication.run(App.class, args);

        /*Long timestamp = System.currentTimeMillis();
        String secret = "SECf3367089ed74e5000e6aa66c1f4729410138a0f06f3da55ad942332c03ba8993";

        String stringToSign = timestamp + "\n" + secret;
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256,secret.getBytes(StandardCharsets.UTF_8));

        byte[] signData = mac.digest(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");


        String url = StrUtil.format(
                        "https://oapi.dingtalk.com/robot/send?access_token={}&timestamp={}&sign={}",
                "3cb4cea36fd59dc7746fc7ab93d2f3d0be1f0486838e2c82322617faaca4db38",
                        timestamp,
                        sign
                );
        JSONObject wapper = new JSONObject();
        wapper.set("msgtype", "text");
        JSONObject object2 = new JSONObject();
        wapper.set("text", object2.set("content", "多模块开发通过明确的模块间依赖关系，使得项目的架构更加清晰。每个模块可以定义自己的接口，其他模块通过依赖接口而不是具体实现，降低了模块之间的耦合度，提高了代码的可维护性。"));
        JSONObject object3 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("");
        wapper.set("at", object3.set("atMobiles", array));

        String post = HttpUtil.post(url, JSONUtil.toJsonStr(wapper));*/
    }

}
