package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.UserOrderDAO;
import main.dao.PerformDAO;
import main.dao.UserDAO;
import main.entity.PerformEntity;
import main.entity.TicketOrderEntity;
import main.service.UserOrderService;
import main.util.DateUtil;
import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
            anOrder.setBackMoney(oneOrder.getBackMoney());

            allOrdersVOList.add(anOrder);
        }


        return allOrdersVOList;
    }


    @Transactional
    public List<OrderVO> getUserOrder(String userId, int type) {
        List<OrderVO> orderVOList = new ArrayList<OrderVO>();
        List<TicketOrderEntity> ticketOrderEntityList = new ArrayList<TicketOrderEntity>();

        ticketOrderEntityList = userOrderDAO.getUserOrders(userId, type);

        TicketOrderEntity orderEntity = null;
        OrderVO orderVO = null;

        Iterator<TicketOrderEntity> iterator = ticketOrderEntityList.iterator();

        while (iterator.hasNext()){
            orderEntity = iterator.next();

            orderVO = new OrderVO();
            orderVO.setOrderId(orderEntity.getOrderId());
            orderVO.setPerformId(orderEntity.getPerform().getId());
            orderVO.setPerformName(orderEntity.getPerform().getName());
            orderVO.setCouponId(orderEntity.getCouponId());
            orderVO.setOrderType(orderEntity.getOrderType());
            orderVO.setTicketMoney(orderEntity.getTicketMoney());
            orderVO.setTicketNum(orderEntity.getTicketNum());
            orderVO.setOrderTime(orderEntity.getOrderTime());
            orderVO.setOrderMoney(orderEntity.getOrderMoney());
            orderVO.setBackMoney(orderEntity.getBackMoney());

            orderVOList.add(orderVO);

        }


        return orderVOList;
    }


    @Transactional
    public ResultMessage cancelOrder(OrderVO orderVO) {
        ResultMessage cancelOrderRe = null;
        ResultMessage updateUserScore = null;
        ResultMessage updateSeat = null;

        int orderId = orderVO.getOrderId();
        int performId = orderVO.getPerformId();
        String userId = orderVO.getUserId();
        int ticketMoney = orderVO.getTicketMoney();
        int ticketNum = orderVO.getTicketNum();
        int seatGrade = 0;

        double orderMoney = orderVO.getOrderMoney();
        double backMoney = 0;

        String performTimeStr = null;
        PerformEntity thePerform = new PerformEntity();
        thePerform = baseDAO.getEntity(PerformEntity.class, performId);

        performTimeStr = thePerform.getTime();

        Timestamp twoWeekBefore = DateUtil.beforeTwoWeek(performTimeStr); //得到演出时间前两周的时间戳

        Timestamp now = new Timestamp(System.currentTimeMillis());
        System.out.println("two week before String:"+performTimeStr);
        System.out.println("two week before timestamp:"+twoWeekBefore);
        /*更新订单状态*/
        if (now.before(twoWeekBefore)) { //申请退款在两周前  退50%
            backMoney = orderMoney * (0.5);
            cancelOrderRe = userOrderDAO.cancelOrder(orderId, backMoney);

        }else { //两周之内 退10%
            backMoney = orderMoney * (0.1);
            cancelOrderRe = userOrderDAO.cancelOrder(orderId, backMoney);

        }

        /*更新用户积分 （余额不考虑了）*/
        updateUserScore = userDAO.updateScore(userId, (int) backMoney, 2);

        /*相应的座位恢复*/
        List<Object[]> priceList = new ArrayList<Object[]>();
        priceList = performDAO.getPrice(performId);
        Object[] performPrice = priceList.get(0);

        for(int i=0;i<performPrice.length;i++) {
            seatGrade++;
            if (ticketMoney == (Integer) performPrice[i]) {
                break;
            }
        }

        updateSeat = performDAO.updatePerformSeat(performId, seatGrade, ticketNum, 1);

        if ((cancelOrderRe == ResultMessage.SUCCESS) && (updateUserScore == ResultMessage.SUCCESS) && (updateSeat == ResultMessage.SUCCESS)) {
            return ResultMessage.SUCCESS;
        }


        return ResultMessage.FAILURE;
    }
}
