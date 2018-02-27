package main.dao;

import main.entity.CouponEntity;
import main.entity.PriceEntity;
import main.entity.UserEntity;
import main.util.ResultMessage;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/20.
 */
public interface UserDAO {

        ResultMessage register(UserEntity userEntity) throws Exception;

        List<String> getAllUserID();

        List<String> getAllMail();

        UserEntity getUserInfo(String userId);

        ResultMessage cancelVIP(String userId);

        ResultMessage changePasswd(String userId, int newPasswd);

        List<CouponEntity> getCoupon(String userId);



}
