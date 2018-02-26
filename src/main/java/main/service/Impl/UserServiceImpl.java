package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.UserDAO;
import main.entity.CouponEntity;
import main.entity.UserEntity;
import main.service.UserService;
import main.util.MailCodeMap;
import main.util.ResultMessage;
import main.vo.CouponVO;
import main.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Created by liyipeng on 2018/2/20.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    BaseDAO baseDAO;


    @Transactional
    public ResultMessage register(UserVO userVO, int code)  {
        ResultMessage result = null;
        List<String> userID = new ArrayList<String>();
        List<String> userMail = new ArrayList<String>();

        int sendCode = 0; //点击发送验证码之后得到的验证码

        sendCode = MailCodeMap.getInstance().getCode(userVO.getId());

        if(sendCode != code){
            return ResultMessage.FAILURE_WRONGMAILCODE;
        }

        userID = userDAO.getAllUserID();
        for(String oneID : userID){
            if(userVO.getId().equals(oneID)){ //user exist
                return ResultMessage.USER_REPEATED;
            }
        }

        userMail = userDAO.getAllMail();
        for(String oneMail : userMail){
            if(userVO.getMail().equals(oneMail)){ //mail already bind with user
                return ResultMessage.FAILURE_MAILREPEATED;
            }
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userVO.getId());
        userEntity.setPassword(userVO.getPassword());
        userEntity.setMail(userVO.getMail());

        userEntity.setVipGrade(1);
        userEntity.setVipIsStop(0); // un stop
        userEntity.setVipScore(0);

        try {
            result = userDAO.register(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MailCodeMap.getInstance().remove(userVO.getId());

        return result;
    }

    @Transactional
    public ResultMessage login(UserVO userVO) {
        String userID = userVO.getId();
        int password = userVO.getPassword();

        UserEntity userEntity = new UserEntity();
        ResultMessage result = null;

        userEntity = baseDAO.getEntity(UserEntity.class, userID);

        if(userEntity == null){
            result = ResultMessage.NONE_USER;
            return result;
        }else {
            if(password != userEntity.getPassword()){
                result = ResultMessage.WRONG_PASSWORD;
                return result;
            }

        }

        result = ResultMessage.SUCCESS;

        return result;
    }


    public ResultMessage sendMailCode(String userID, String mail) {
        int code = 0;
        code = new Random().nextInt(999999);
        if(code < 100000){
            code += 100000;
        }

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
            message.setContent("恭喜您中奖 激活码为:"+code, "text/html;charset=utf-8");

            Transport transport = session.getTransport();
            transport.connect(from, password);
            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MailCodeMap.getInstance().put(userID, code);

        return ResultMessage.SUCCESS;
    }


    @Transactional
    public UserVO getUserInfo(String userID) {
        UserEntity theUser = new UserEntity();
        theUser = userDAO.getUserInfo(userID);

        UserVO userVO = new UserVO();

        userVO.setMail(theUser.getMail());
        userVO.setVipGrade(theUser.getVipGrade());
        userVO.setVipIsStop(theUser.getVipIsStop());
        userVO.setVipScore(theUser.getVipScore());

        return userVO;
    }


    @Transactional
    public ResultMessage cancelVIP(String userID) {
        ResultMessage result= null;
        result = userDAO.cancelVIP(userID);

        return result;
    }

    @Transactional
    public ResultMessage changePasswd(String userId, int oldPasswd, int newPasswd) {
        UserEntity theUser = new UserEntity();
        theUser = baseDAO.getEntity(UserEntity.class, userId);

        if(theUser.getPassword() != oldPasswd){
            return ResultMessage.WRONG_PASSWORD;
        }

        ResultMessage result = null;
        result = userDAO.changePasswd(userId, newPasswd);

        return result;
    }

    @Transactional
    public List<CouponVO> getCoupon(String userId) {
        List<CouponEntity> allCoupon = new ArrayList<CouponEntity>();
        List<CouponVO> couponVOList = new ArrayList<CouponVO>();

        allCoupon = userDAO.getCoupon(userId);
        CouponVO couponVO = new CouponVO();

        for (CouponEntity couponEntity: allCoupon){
            couponVO.setCouponID(couponEntity.getCouponId());
            couponVO.setMoney(couponEntity.getMoney());
            couponVO.setType(couponEntity.getType());
            couponVO.setUserId(couponEntity.getUserId());
            if(couponEntity.getIsUse() == 1){
                couponVO.setUse(true);
            }else {
                couponVO.setUse(false);
            }
            couponVO.setBeginDate(couponEntity.getBeginDate());
            couponVO.setEndDate(couponEntity.getEndDate());

            couponVOList.add(couponVO);
            couponVO = new CouponVO();
        }

        return couponVOList;
    }


}
