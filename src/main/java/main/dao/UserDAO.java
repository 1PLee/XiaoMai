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

        ResultMessage convertCoupon(CouponEntity couponEntity);

        ResultMessage updateScore(String userId, int needScore); //兑换优惠券后减少会员积分

        ResultMessage updateCouponState(String userId, int couponId);//使用优惠券后更改优惠券状态

}
