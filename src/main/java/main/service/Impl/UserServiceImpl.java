package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.UserDAO;
import main.entity.CouponEntity;
import main.entity.UserEntity;
import main.service.UserService;
import main.util.*;
import main.vo.CouponVO;
import main.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.sql.Timestamp;
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

        if(SendMailCode.sendMailCode(mail,code) == ResultMessage.SUCCESS){
            MailCodeMap.getInstance().put(userID, code);
            return ResultMessage.SUCCESS;
        }


        return ResultMessage.FAILURE;

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

    @Transactional
    public ResultMessage convertCoupon(String description, String time, String userId, int needScore) {

        ResultMessage result = null;
        CouponVO couponVO = new CouponVO();
        CouponEntity couponEntity = new CouponEntity();
        List<Integer> couponInfo = new ArrayList<Integer>();


        couponInfo = Compile.getAllInteger(description);

        switch (couponInfo.get(0)) {
            case 100:
                couponVO.setType(1);
                break;
            case 200:
                couponVO.setType(2);
                break;
            case 300:
                couponVO.setType(3);
                break;
            case 1000:
                couponVO.setType(10);
                break;
        }

        couponVO.setMoney(couponInfo.get(1));
        couponVO.setUse(false);
        couponVO.setUserId(userId);

        String[] useTime = time.split("--");

        Timestamp beginDate = DateUtil.String2Timestamp(useTime[0]);
        Timestamp endDate = DateUtil.String2Timestamp(useTime[1]);

        couponVO.setBeginDate(beginDate);
        couponVO.setEndDate(endDate);

        couponEntity.setBeginDate(couponVO.getBeginDate());
        couponEntity.setEndDate(couponVO.getEndDate());
        couponEntity.setIsUse(0);
        couponEntity.setMoney(couponVO.getMoney());
        couponEntity.setType(couponVO.getType());
        couponEntity.setUserId(couponVO.getUserId());

        result = userDAO.convertCoupon(couponEntity);

        if(result == ResultMessage.SUCCESS){
            result = userDAO.updateScore(userId, needScore);
        }

        return result;
    }


}
