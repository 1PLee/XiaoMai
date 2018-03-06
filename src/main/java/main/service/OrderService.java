package main.service;

import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface OrderService {

    CreateOrderResultVO createOrder(OrderVO orderVO);

    /*确认订单支付  如果账户密码无误， 扣钱 加积分*/
    ResultMessage payOrder(UserMoneyVO userMoneyVO, double orderMoney, String userId, int orderId);
}
