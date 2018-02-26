package main.controller;

import main.service.UserService;
import main.util.ResultMessage;
import main.vo.UserVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liyipeng on 2018/2/9.
 */
@Controller
@RequestMapping(value ="/User")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/sendMailCode", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject sendMailCode(@RequestParam("userID") String userID, @RequestParam("mail") String mail){ //发送邮箱验证码

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("result", userService.sendMailCode(userID, mail));
        return jsonObject;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestBody UserVO userVO){
        ResultMessage result = null;
        int code = 0;
        code = userVO.getMailCode();


        result = userService.register(userVO, code);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("result", result);

        return jsonObject;

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody UserVO userVO){
        ResultMessage result = null;
        result = userService.login(userVO);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);

        return jsonObject;
    }


    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserInfo(@RequestParam("userId") String userID){

        UserVO theUser = new UserVO();
        theUser = userService.getUserInfo(userID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", theUser);

        return jsonObject;
    }

    @RequestMapping(value = "/cancelVIP", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject cancelVIP(@RequestParam("userId") String userID) {
        ResultMessage result = null;
        JSONObject jsonObject = new JSONObject();

        result = userService.cancelVIP(userID);

        jsonObject.put("cancelResult", result);

        return jsonObject;
    }



}
