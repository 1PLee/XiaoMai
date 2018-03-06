package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.UserOrderDAO;
import main.dao.PerformDAO;
import main.dao.UserDAO;
import main.entity.PerformEntity;
import main.entity.TicketOrderEntity;
import main.service.UserOrderService;
import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/3/1.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    UserOrderDAO userOrderDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PerformDAO performDAO;

    @Autowired
    BaseDAO baseDAO;

    @Transactional
    public CreateOrderResultVO createOrder(OrderVO orderVO) {
        CreateOrderResultVO resultVO = new CreateOrderResultVO();

        TicketOrderEntity orderEntity = new TicketOrderEntity();
        PerformEntity thePerform = new PerformEntity();

        int couponId = orderVO.getCouponId();
        String userId = orderVO.getUserId();
        int performId = orderVO.getPerformId();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp shouldPay = new Timestamp(System.currentTimeMillis() + 900000); //900000ms代表15分钟


        thePerform = baseDAO.getEntity(PerformEntity.class, performId);

        orderEntity.setUserId(orderVO.getUserId());
        orderEntity.setPerform(thePerform);

         /*不管支付成功与否 优惠券都标记为被使用*/
        if(couponId != 0){

            if(userDAO.updateCouponState(userId, couponId) != ResultMessage.SUCCESS){

                resultVO.setResult(ResultMessage.FAILURE_COUPONUPDATE);
                resultVO.setOrderId(0);
                return resultVO;
            }

        }

        orderEntity.setCouponId(couponId);
        orderEntity.setTicketNum(orderVO.getTicketNum());
        orderEntity.setTicketMoney(orderVO.getTicketMoney());

        orderEntity.setOrderMoney(orderVO.getOrderMoney());
        orderEntity.setOrderType(0); //待支付
        orderEntity.setOrderTime(now);
        orderEntity.setShouldPay(shouldPay);
        orderEntity.setBackMoney(0.0);//默认为0

        /*对相应的perform的座位进行锁单*/
        if (performDAO.updatePerformSeat(performId, orderVO.getTicketSeat(), orderVO.getTicketNum(), 0) == ResultMessage.FAILURE_NONESEAT) {
            resultVO.setResult(ResultMessage.FAILURE_NONESEAT);
            resultVO.setOrderId(0);

            return resultVO;
        }

        int orderId = userOrderDAO.createOrder(orderEntity);
        resultVO.setOrderId(orderId);
        resultVO.setResult(ResultMessage.SUCCESS);

        return resultVO;
    }


    @Transactional
    public ResultMessage payOrder(UserMoneyVO userMoneyVO, double orderMoney, String userId, int orderId) {
        ResultMessage check = userOrderDAO.checkPayUser(userMoneyVO);
        String payUser = userMoneyVO.getUserId();

        if(check != ResultMessage.SUCCESS){
            return check;
        }

        ResultMessage payOrderResult = userOrderDAO.updatePayMoney(payUser, orderMoney);

        if(payOrderResult != ResultMessage.SUCCESS){
            return payOrderResult;
        }

        ResultMessage updateScoreResult = null;


        updateScoreResult = userDAO.updateScore(userId, (int) orderMoney, 1);

        if(updateScoreResult != ResultMessage.SUCCESS){
            return updateScoreResult;
        }

        ResultMessage confirmPay = userOrderDAO.confirmOrderPay(orderId);


        return confirmPay;
    }

    @Transactional
    public List<OrderVO> getAllOrders(String userId) {
        List<OrderVO> allOrdersVOList = new ArrayList<OrderVO>();
        List<TicketOrderEntity> allOrders = new ArrayList<TicketOrderEntity>();
        allOrders = userOrderDAO.getAllOrders(userId);

        TicketOrderEntity oneOrder = null;
        OrderVO  anOrder = null;

        Iterator<TicketOrderEntity> iterator = allOrders.iterator();

        while (iterator.hasNext()){
            oneOrder = iterator.next();

            anOrder = new OrderVO();
            anOrder.setOrderId(oneOrder.getOrderId());
            anOrder.setCouponId(oneOrder.getCouponId());
            anOrder.setOrderMoney(oneOrder.getOrderMoney());
            anOrder.setOrderTime(oneOrder.getOrderTime());
            anOrder.setOrderType(oneOrder.getOrderType());
            anOrder.setPerformId(oneOrder.getPerform().getId());
            anOrder.setPerformName(oneOrder.getPerform().getName());
            anOrder.setTicketNum(oneOrder.getTicketNum());
            anOrder.setTicketMoney(oneOrder.getTicketMoney());

            allOrdersVOList.add(anOrder);
        }


        return allOrdersVOList;
    }
}
