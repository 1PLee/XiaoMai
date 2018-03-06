package main.service.Impl;

import main.dao.OrderDAO;
import main.dao.PerformDAO;
import main.dao.UserDAO;
import main.entity.TicketOrderEntity;
import main.entity.UserMoneyEntity;
import main.service.OrderService;
import main.util.ResultMessage;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by liyipeng on 2018/3/1.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PerformDAO performDAO;

    @Transactional
    public ResultMessage createOrder(OrderVO orderVO) {
        TicketOrderEntity orderEntity = new TicketOrderEntity();
        int couponId = orderVO.getCouponId();
        String userId = orderVO.getUserId();
        int performId = orderVO.getPerformId();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp shouldPay = new Timestamp(System.currentTimeMillis() + 900000); //900000ms代表15分钟


        orderEntity.setUserId(orderVO.getUserId());

         /*不管支付成功与否 优惠券都标记为被使用*/
        if(couponId != 0){

            if(userDAO.updateCouponState(userId, couponId) == ResultMessage.SUCCESS){
                orderEntity.setCouponId(orderVO.getCouponId());
            }else {
                return ResultMessage.FAILURE_COUPONUPDATE;
            }

        }

        orderEntity.setPerformId(performId);
        orderEntity.setTicketNum(orderVO.getTicketNum());
        orderEntity.setTicketMoney(orderVO.getTicketMoney());
        System.out.println("look the orderMoney:"+ orderVO.getOrderMoney());
        orderEntity.setOrderMoney(orderVO.getOrderMoney());
        orderEntity.setOrderType(0); //待支付
        orderEntity.setOrderTime(now);
        orderEntity.setShouldPay(shouldPay);
        orderEntity.setBackMoney(0.0);//默认为0

        /*对相应的perform的座位进行锁单*/
        if (performDAO.updatePerformSeat(performId, orderVO.getTicketSeat(), orderVO.getTicketNum(), 0) == ResultMessage.FAILURE_NONESEAT) {

            return ResultMessage.FAILURE_NONESEAT;
        }


        ResultMessage result = null;
        result = orderDAO.createOrder(orderEntity);

        return result;
    }


    @Transactional
    public ResultMessage payOrder(UserMoneyVO userMoneyVO, double orderMoney, String userId) {
        ResultMessage check = orderDAO.checkPayUser(userMoneyVO);
        String payUser = userMoneyVO.getUserId();

        if(check != ResultMessage.SUCCESS){
            return check;
        }

        ResultMessage payOrderResult = orderDAO.updatePayMoney(payUser, orderMoney);

        if(payOrderResult != ResultMessage.SUCCESS){
            return payOrderResult;
        }

        ResultMessage updateScoreResult = null;


        updateScoreResult = userDAO.updateScore(userId, (int) orderMoney, 1);


        return updateScoreResult;
    }
}
