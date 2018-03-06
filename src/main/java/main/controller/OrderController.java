package main.controller;

import main.service.OrderService;
import main.util.ResultMessage;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liyipeng on 2018/3/1.
 */
@Controller
@RequestMapping(value = "/Order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public String createOrder(@RequestBody OrderVO orderVO){

        ResultMessage result = null;
        result = orderService.createOrder(orderVO);

        return result.toShow();
    }


    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    @ResponseBody
    public String payOrder(@RequestBody JSONObject jsonObject){
        UserMoneyVO userMoneyVO = new UserMoneyVO();
        String userPayName = (String)jsonObject.get("userName");
        int password = (Integer) jsonObject.get("password");
        double orderMoney = (Double) jsonObject.get("orderMoney");
        String userId = (String) jsonObject.get("userId");

        userMoneyVO.setUserId(userPayName);
        userMoneyVO.setPassword(password);

        ResultMessage result = null;
        result = orderService.payOrder(userMoneyVO, orderMoney, userId);


        return result.toShow();
    }


}
