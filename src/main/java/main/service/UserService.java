package main.service;

import main.util.ResultMessage;
import main.vo.CouponVO;
import main.vo.UserVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/20.
 */
public interface UserService {

    ResultMessage register(UserVO userVO, int code);

    ResultMessage login(UserVO userVO);

    ResultMessage sendMailCode(String userID,String mail);

    UserVO getUserInfo(String userID);

    ResultMessage cancelVIP(String userID);

    ResultMessage changePasswd(String userId, int oldPasswd, int newPasswd);

    List<CouponVO> getCoupon(String userId);

    ResultMessage convertCoupon(String description, String time ,String userId, int needScore);

}
