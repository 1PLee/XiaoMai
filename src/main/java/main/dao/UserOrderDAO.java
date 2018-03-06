package main.dao;

import main.entity.TicketOrderEntity;
import main.entity.UserMoneyEntity;
import main.util.ResultMessage;
import main.vo.UserMoneyVO;
import main.vo.UserVO;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface UserOrderDAO {

    int createOrder(TicketOrderEntity orderEntity); //返回orderId 失败返回0

    ResultMessage checkPayUser(UserMoneyVO userMoneyVO); //检查支付账号密码是否正确

    ResultMessage updatePayMoney(String userName, double orderMoney); //更新支付账户余额

    ResultMessage confirmOrderPay(int orderId); //确认订单支付


    /*根据TicketOrderEntity 中的 orderType 返回不同类型的 订单*/
    List<TicketOrderEntity> getUserOrders(String userId, int type);

    List<TicketOrderEntity> getAllOrders(String userId);

    List<TicketOrderEntity> getAllUnPayOrders(String userId); //某个用户所有待支付订单

    List<TicketOrderEntity> getAllBackOrders(String userId); //得到某个用户所有退款的订单


}
