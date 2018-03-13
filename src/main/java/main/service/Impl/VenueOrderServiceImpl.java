package main.service.Impl;

import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.dao.UserOrderDAO;
import main.entity.PerformEntity;
import main.entity.TicketOrderEntity;
import main.service.UserOrderService;
import main.service.VenueOrderService;
import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;
import main.vo.UserMoneyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by liyipeng on 2018/3/13.
 */
@Service
public class VenueOrderServiceImpl implements VenueOrderService {

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    PerformDAO performDAO;

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    UserOrderDAO userOrderDAO;


    @Transactional
    public CreateOrderResultVO buyTicketOnSite(OrderVO orderVO) {

        String userId = orderVO.getUserId();

        double orderMoney = orderVO.getOrderMoney();
        CreateOrderResultVO createOrderResultVO = null;
        ResultMessage payOrder = null;

        /* orderVO中 已经存在 userId performId ticketMoney ticketNum orderMoney */
        orderVO.setCouponId(0); //无论是不是会员 现场都不能使用优惠券

        if(userId.equals("Manager")){ //非会员用管理员账号现场支付
            createOrderResultVO = managerCreateOrder(orderVO);

            return createOrderResultVO;


        }else { //会员优惠

            createOrderResultVO = userOrderService.createOrder(orderVO); //得到创建成功后的orderId

            if(createOrderResultVO.getResult() != ResultMessage.SUCCESS){
                return createOrderResultVO;
            }

            int orderId = createOrderResultVO.getOrderId();

        /* 现场结算 默认userMoneyVO为这个账号 */
            UserMoneyVO userMoneyVO = new UserMoneyVO();
            userMoneyVO.setUserId("lypXiao");
            userMoneyVO.setPassword(111111);


            payOrder = userOrderService.payOrder(userMoneyVO, orderMoney, userId, orderId);

        }

        if(payOrder != ResultMessage.SUCCESS){

            createOrderResultVO.setResult(ResultMessage.FAILURE);
        }


        return createOrderResultVO;

    }

    public CreateOrderResultVO managerCreateOrder(OrderVO orderVO) {

        CreateOrderResultVO resultVO = new CreateOrderResultVO();

        TicketOrderEntity orderEntity = new TicketOrderEntity();
        PerformEntity thePerform = new PerformEntity();

        int couponId = orderVO.getCouponId(); //为0
        String userId = orderVO.getUserId();
        int performId = orderVO.getPerformId();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp shouldPay = new Timestamp(System.currentTimeMillis() + 900000); //900000ms代表15分钟


        thePerform = baseDAO.getEntity(PerformEntity.class, performId);

        orderEntity.setUserId(orderVO.getUserId());
        orderEntity.setPerform(thePerform);



        orderEntity.setCouponId(couponId); //不允许使用优惠券
        orderEntity.setTicketNum(orderVO.getTicketNum());
        orderEntity.setTicketMoney(orderVO.getTicketMoney());

        orderEntity.setOrderMoney(orderVO.getOrderMoney());
        orderEntity.setOrderType(1); // 现场确认支付
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
}
