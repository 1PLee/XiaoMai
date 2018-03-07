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

    ResultMessage cancelOrder(int orderId, double backMoney); //进行订单退款操作


}
