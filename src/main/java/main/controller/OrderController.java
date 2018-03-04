package main.controller;

import main.service.OrderService;
import main.util.ResultMessage;
import main.vo.OrderVO;
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
}
