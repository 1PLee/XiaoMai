package main.service;

import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface UserOrderService {

    CreateOrderResultVO createOrder(OrderVO orderVO);

    /*确认订单支付  如果账户密码无误， 扣钱 加积分*/
    ResultMessage payOrder(UserMoneyVO userMoneyVO, double orderMoney, String userId, int orderId);

    List<OrderVO> getAllOrders(String userId); //得到用户全部订单

    List<OrderVO> getUserOrder(String userId, int type); //得到用户的不同类型的订单

    ResultMessage cancelOrder(OrderVO orderVO); //用户退款

    List<Double> getOrderCount(String userId);// 获取用户的消费统计

    ResultMessage checkOrder(int orderId); //现场检票
}
