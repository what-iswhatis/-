package com.rocky.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.gmw.exception.BizException;
import com.gmw.threadlocal.LocalUserUtil;
import com.rocky.dao.*;
import com.rocky.enums.PoStatus;

import com.rocky.model.*;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@Service
public class PoService {

    @Autowired
    private PoMasterDao poMasterDao ;
    @Autowired
    private PoItemDao poItemDao ;

    @Value("${po.mount.limit}")
    public BigDecimal poMountLimit;

    public List<PoItem> selectItems(PoItemQuery query) {
        return poItemDao.select(query);
    }

    public List<PoMaster> select(PoMasterQuery query) {
        return poMasterDao.select(query);
    }

    public Integer insert(PoMaster master) throws Exception {
        String poId =  "PO"+ DateUtil.format(DateUtil.date(),"yyyyMMddHHmmssSSS");
        master.setId(poId);
        master.setPurchaser(LocalUserUtil.get().getNickName());
        master.setStatus(PoStatus.NEW.getCode());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (PoItem item : master.getItems()) {
            item.setPoId(poId);
            item.setLastUpdateBy(LocalUserUtil.get().getNickName());
            item.setLastUpdateTime(LocalDateTimeUtil.now());
            totalPrice = totalPrice.add(item.getPrice().multiply(new BigDecimal(item.getQty())));
        }
        if(master.getTotalPrice().doubleValue() != totalPrice.doubleValue()){
            throw  new BizException(701,"总价不一致");
        }

        if(master.getTotalPrice().compareTo(poMountLimit) > 0){
            // 需要审核
            sendDingding(master);
        } else {
            master.setStatus(PoStatus.APPROVE.getCode());
        }

        // Object this2 = this; 自己玩自己不行
        PoService aop =   (PoService)AopContext.currentProxy();

        aop.insertAll(master);

        return 1;
    }

    @Transactional
    public void insertAll(PoMaster master)  {
        poMasterDao.insert(master);
        poItemDao.insertList(master.getItems());
    }
    @SneakyThrows
    private void sendDingding(PoMaster master) {
        Long timestamp = System.currentTimeMillis();
        String secret = "SECf3367089ed74e5000e6aa66c1f4729410138a0f06f3da55ad942332c03ba8993";
        String stringToSign = timestamp + "\n" + secret;
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256, secret.getBytes(StandardCharsets.UTF_8));
        byte[] digest = mac.digest(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(digest)), "UTF-8");
        String url = StrUtil.format("https://oapi.dingtalk.com/robot/send?access_token={}&timestamp={}&sign={}",
                "3cb4cea36fd59dc7746fc7ab93d2f3d0be1f0486838e2c82322617faaca4db38",
                timestamp,
                sign);
        JSONObject wapper = new JSONObject();
        wapper.set("msgtype", "text");
        JSONObject object2 = new JSONObject();
        wapper.set("text", object2.set("content",
                StrUtil.format("采购单[{}],总价￥{}元,需要您的审核.", master.getId(), master.getTotalPrice())));
        JSONObject object3 = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("13460197940");
        wapper.set("at", object3.set("atMobiles", array));
        String post = HttpUtil.post(url, JSONUtil.toJsonStr(wapper));
    }
    public Integer update(PoMaster model) {
        return poMasterDao.update(model);
    }

    public Integer delete(Integer id) {
        return poMasterDao.delete(id);
    }

    public Integer sendMail(String id) {
        PoMasterQuery query = new PoMasterQuery();
        query.setId(id);
        List<PoMaster> poMasterList = poMasterDao.select(query);
        PoMaster poMaster = poMasterList.get(0);
        MailAccount account = new MailAccount();
        account.setHost("smtp.aliyun.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("61813631@qq.com");
        account.setUser("61813631@qq.com");
        account.setPass("By123456");
        String subject = StrUtil.format("采购单[{}]，请及时配送.", poMaster.getId());
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<table border='1'>");
        htmlTable.append("<tr>");
        htmlTable.append("<td>").append("采购日期").append("</td>");
        htmlTable.append("<td>").append(poMaster.getPoDate()).append("</td>");
        htmlTable.append("</tr>");
        htmlTable.append("<tr>");
        htmlTable.append("<td>").append("总金额").append("</td>");
        htmlTable.append("<td>").append(poMaster.getTotalPrice()).append("</td>");
        htmlTable.append("</tr>");
        htmlTable.append("</table>");
        String content = htmlTable.toString();
        PoItemQuery query2 = new PoItemQuery();
        query2.setPoId(id);
        List<PoItem> items = poItemDao.select(query2);
        String filePath = "d:/"+poMaster.getId()+".xlsx";
        ExcelWriter writer = ExcelUtil.getWriter(filePath);
        //自定义标题别名
        writer.addHeaderAlias("poId", "采购单号");
        writer.addHeaderAlias("productName", "商品名称");
        writer.addHeaderAlias("qty", "数量");
        writer.addHeaderAlias("price", "单价");
        writer.addHeaderAlias("brief", "备注");
        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(items, true);
        // 关闭writer，释放内存
        writer.close();
        File file = new File(filePath);
        MailUtil.send(account, CollUtil.newArrayList("61813631@qq.com"), subject, content, true, file);
        FileUtil.del(file);
        return 1;
    }
}
