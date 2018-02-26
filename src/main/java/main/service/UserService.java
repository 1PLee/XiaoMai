package main.service;

import main.util.ResultMessage;
import main.vo.UserVO;

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
}
