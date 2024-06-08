package com.mzl.insta360demo.util;

import org.springframework.messaging.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @Description: 邮箱工具类
 * @Author: mzl
 */
public class EmailUtil {

    /**
     * 发件人的邮箱，你自己的
     */
    private static final String EMAIL_FROM = "2198902814@qq.com";

    /**
     * 发件人的授权码
     */
    private static final String EMAIL_PASSWORD = "jrbqchuoppzaecdg";

    /**
     * 发送邮件服务地址
     */
    private static final String EMAIL_SMTP_HOST = "smtp.qq.com";

    /**
     * 收件人的邮箱
     */
    private static final String EMAIL_TO = "2198902814@qq.com";

    /**
     * 发送邮件对象，发送创建好的邮件内容
     */
    public static void sendEmail(String content) throws IOException, MessagingException, javax.mail.MessagingException {
        // 创建配置参数，用于连接邮件服务器的参数配置
        Properties properties = new Properties();
        // 开启debug模式
        properties.setProperty("mail.debug", "true");
        // 发送邮件需要身份验证
        properties.setProperty("mail.smtp.auth", "true");
        // 设置服务器主机名
        properties.setProperty("mail.smtp.host", EMAIL_SMTP_HOST);
        // 设置端口号，qq邮箱给出了两个端口号，但是465端口号不能用，可以使用587端口号
        properties.setProperty("mail.smtp.port", "587");
        // 用户身份验证
        // 此处是发件人的邮箱地址
        properties.setProperty("mail.user", EMAIL_FROM);
        // 此处的发件的服务器密码
        properties.setProperty("mail.password", EMAIL_PASSWORD);
        // 发送邮件协议的名称
        properties.setProperty("mail.transport.protocol", "smtp");

        // 构建授权信息，用于smtp的身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名和密码
                String userName = properties.getProperty("mail.user");
                String passowrd = properties.getProperty("mail.password");
                PasswordAuthentication passwordAuthentication = new PasswordAuthentication(userName, passowrd);
                return passwordAuthentication;
            }
        };

        // 根据邮件配置创建邮箱服务器会话， 用于和邮箱服务器交互
        Session session = Session.getInstance(properties, authenticator);
        // 设置debug，查看发送的过程的bug信息
        session.setDebug(true);

        // 创建获取邮件对象，并处理邮件
        MimeMessage mimeMessage = createMimeMessage(session, EMAIL_FROM, EMAIL_TO, content);

        // 根据session获取传输对象
        Transport transport = session.getTransport();
        // 使用用户名和密码连接服务器，和发件人的真正邮箱必须一致，不然会发送不了，失败
        transport.connect(EMAIL_FROM, EMAIL_PASSWORD);
        // 发送邮件，发送到所有的收件人邮箱时
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        // 发送邮件后，关闭传输对象
        transport.close();
    }

    /**
     * 创建邮件对象，处理邮件的内容
     */
    private static MimeMessage createMimeMessage(Session session, String emailFrom, String emailTo, String content) throws MessagingException, IOException, javax.mail.MessagingException {
        // 创建一封邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        // 设置发件人
        mimeMessage.setFrom(new InternetAddress(emailFrom, "【抢X3任务】", "UTF-8"));
        // 设置收件人
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailTo, "", "UTF-8"));
        // 设置邮件主题
        mimeMessage.setSubject("抢X3产品通知", "UTF-8");
        // 邮箱正文内容（此次使用普通文本的格式内容）
        mimeMessage.setText(content);
        // 设置发送的时间
        mimeMessage.setSentDate(new Date());
        // 保存设置
        mimeMessage.saveChanges();

        return mimeMessage;
    }

}