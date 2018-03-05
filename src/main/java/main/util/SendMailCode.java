package main.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by liyipeng on 2018/3/5.
 */
public class SendMailCode {

    public static ResultMessage sendMailCode(String mail, int code){
        final String from = "1017270947@qq.com";
        final String password = "zuahyzeeokejbfdj";// 授权码
        String host = "smtp.qq.com";
        Properties properties = System.getProperties(); //获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true"); // 打开认证
        //properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.password", password);



        try {
            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        });

        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            message.setSubject("激活码");
            message.setContent("您注册所使用的 激活码为:"+code, "text/html;charset=utf-8");

            Transport transport = session.getTransport();
            transport.connect(from, password);
            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return ResultMessage.SUCCESS;
    }
}
