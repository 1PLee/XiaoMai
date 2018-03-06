package main.controller;

import main.service.UserOrderService;
import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyipeng on 2018/3/1.
 *
 * 有关用户订单的相关内容
 */


@Controller
@RequestMapping(value = "/UserOrder")
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public CreateOrderResultVO createOrder(@RequestBody OrderVO orderVO){

        CreateOrderResultVO resultVO = new CreateOrderResultVO();
        resultVO = userOrderService.createOrder(orderVO);

        return resultVO;
    }


    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    @ResponseBody
    public String payOrder(@RequestBody JSONObject jsonObject){
        UserMoneyVO userMoneyVO = new UserMoneyVO();
        String userPayName = (String)jsonObject.get("userName");
        int password = (Integer) jsonObject.get("password");
        double orderMoney = (Double) jsonObject.get("orderMoney");
        String userId = (String) jsonObject.get("userId");
        int orderId = (Integer) jsonObject.get("orderId");

        userMoneyVO.setUserId(userPayName);
        userMoneyVO.setPassword(password);

        ResultMessage result = null;
        result = userOrderService.payOrder(userMoneyVO, orderMoney, userId, orderId);

        return result.toShow();
    }

    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OrderVO>> getAllOrders(@RequestParam("userId") String userId){
        Map<String, List<OrderVO>> resultMap = new HashMap<String, List<OrderVO>>();

        List<OrderVO> orderVOList = new ArrayList<OrderVO>();

        orderVOList = userOrderService.getAllOrders(userId);

        resultMap.put("data", orderVOList);

        return resultMap;
    }






}
