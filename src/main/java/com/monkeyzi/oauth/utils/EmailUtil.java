package com.monkeyzi.oauth.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.monkeyzi.oauth.entity.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

/**
 * 发送邮件工具类
 */
@Slf4j
@Component
public class EmailUtil {

    private  String  email_Host="smtp.qq.com";
    private  String  userName="854152531@qq.com";
    private  String  password="tkrnxuwdqawmbedg";

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 异步发送邮件
     * @param sendTo 发送给谁
     * @param title  邮件主题
     * @param templateName 模板名
     * @param o      模板替换对象
     */
    public  void  sendTemplateEmail(String sendTo, String title, String templateName, Object o){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        //设定邮箱服务器配置
        javaMailSender.setHost(email_Host);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);
        Properties prop = new Properties();

        //服务器进行认证
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "20000");
        //邮箱发送服务器端口,这里设置为465端口
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        javaMailSender.setJavaMailProperties(prop);

        //发送html邮件
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        //设置邮件内容
        try {
            messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");

            messageHelper.setTo(sendTo);
            messageHelper.setFrom(userName);
            messageHelper.setSubject(title);
            Context context = new Context();
            context.setVariables(beanToMap(o));
            // 获取模板html代码
            String content = templateEngine.process(templateName, context);
            // true表示HTML格式的邮件
            messageHelper.setText(content, true);
            // 发送邮件
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
    public static void main(String[] args) {
        EmailUtil util=new EmailUtil();
         User user=new User();
         user.setUsername("高艳国");
         util.sendTemplateEmail("18322596359@163.com","测试邮件","test-email",user);
    }
}
