package main.dao;

import main.entity.TicketOrderEntity;
import main.entity.UserMoneyEntity;
import main.util.ResultMessage;
import main.vo.UserMoneyVO;
import main.vo.UserVO;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface OrderDAO {

    int createOrder(TicketOrderEntity orderEntity); //返回orderId 失败返回0

    ResultMessage checkPayUser(UserMoneyVO userMoneyVO); //检查支付账号密码是否正确

    ResultMessage updatePayMoney(String userName, double orderMoney); //更新支付账户余额

    ResultMessage confirmOrderPay(int orderId); //确认订单支付


}
