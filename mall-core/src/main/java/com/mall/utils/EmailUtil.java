package com.mall.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailUtil {

    /**
     * 邮件服务器
     */
    @Value("${email.host}")
    private String host;

    /**
     * 邮件服务器端口，默认25
     */
    @Value("${email.port:25}")
    private Integer port;

    /**
     * 发送方邮箱地址
     */
    @Value("${email.form}")
    private String form;

    /**
     * 发送方邮箱账号
     */
    @Value("${email.user}")
    private String user;

    /**
     * 发送方邮箱密码
     */
    @Value("${email.pwd}")
    private String pwd;

    /**
     * @param mailTo  接收方邮箱列表
     * @param subject 标题
     * @param content 内容
     * @param isHtml  是否转化为html格式
     */
    public void send(List<String> mailTo, String subject, String content, boolean isHtml) {
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(port);
        account.setAuth(true);
        account.setFrom(form);
        account.setUser(user);
        account.setPass(pwd);
        MailUtil.send(account, mailTo, subject, content, isHtml);
    }

}