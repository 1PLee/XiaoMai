package main.controller;

import com.mchange.v2.util.DoubleWeakHashMap;
import com.sun.tools.corba.se.idl.constExpr.Or;
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

    @RequestMapping(value = "/getUnPayOrders", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OrderVO>> getUnPayOrders(@RequestParam("userId") String userId){
        Map<String, List<OrderVO>> resultMap = new HashMap<String, List<OrderVO>>();

        List<OrderVO> orderVOList = new ArrayList<OrderVO>();

        orderVOList = userOrderService.getUserOrder(userId, 0);

        resultMap.put("data", orderVOList);

        return resultMap;
    }


    /*获得用户已经完成的（已检票）的订单*/
    @RequestMapping(value = "/getCompleteOrders", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OrderVO>> getCompleteOrders(@RequestParam("userId") String userId){
        Map<String, List<OrderVO>> resultMap = new HashMap<String, List<OrderVO>>();

        List<OrderVO> orderVOList = new ArrayList<OrderVO>();

        orderVOList = userOrderService.getUserOrder(userId, 2);

        resultMap.put("data", orderVOList);

        return resultMap;
    }

    /*获得用户已经支付(还没有完成)的订单*/
    @RequestMapping(value = "/getPayOrders", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OrderVO>> getPayOrders(@RequestParam("userId") String userId){
        Map<String, List<OrderVO>> resultMap = new HashMap<String, List<OrderVO>>();

        List<OrderVO> orderVOList = new ArrayList<OrderVO>();

        orderVOList = userOrderService.getUserOrder(userId, 1);

        resultMap.put("data", orderVOList);

        return resultMap;
    }

    @RequestMapping(value = "/getInvalidOrders", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OrderVO>> getInvalidOrders(@RequestParam("userId") String userId){
        Map<String, List<OrderVO>> resultMap = new HashMap<String, List<OrderVO>>();

        List<OrderVO> orderVOCancelList = new ArrayList<OrderVO>(); //逾期未支付订单
        List<OrderVO> orderVOBackList = new ArrayList<OrderVO>(); //申请退款订单
        List<OrderVO> orderVOList = new ArrayList<OrderVO>();

        orderVOCancelList = userOrderService.getUserOrder(userId, 3);
        orderVOBackList = userOrderService.getUserOrder(userId, 4);

        orderVOList.addAll(orderVOCancelList);
        orderVOList.addAll(orderVOBackList);

        resultMap.put("data", orderVOList);

        return resultMap;
    }


    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrder(@RequestBody OrderVO orderVO){

        ResultMessage result = null;

        result = userOrderService.cancelOrder(orderVO);

        return result.toShow();
    }


    /*获取用户的消费统计*/
    @RequestMapping(value = "/getUserOrderCount", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Double>> getUserOrderCount(@RequestParam("userId") String userId) {
        Map<String, List<Double>> resultMap = new HashMap<String, List<Double>>();

        List<Double> countList = new ArrayList<Double>();
        countList = userOrderService.getOrderCount(userId);

        resultMap.put("data", countList);

        return resultMap;
    }




}
