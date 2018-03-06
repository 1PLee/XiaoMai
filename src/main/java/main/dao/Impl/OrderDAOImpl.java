package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.OrderDAO;
import main.entity.TicketOrderEntity;
import main.entity.UserMoneyEntity;
import main.util.ResultMessage;
import main.vo.UserMoneyVO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by liyipeng on 2018/3/1.
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }


    public int createOrder(TicketOrderEntity orderEntity) {
        try {
            int resultId = (Integer) baseDAO.save(orderEntity);
            System.out.println("look resultId: " + resultId);
            if(resultId >0){
                return resultId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ResultMessage checkPayUser(UserMoneyVO userMoneyVO) {
        UserMoneyEntity theUser = new UserMoneyEntity();
        String userId = userMoneyVO.getUserId();
        int password = 0;

        theUser = baseDAO.getEntity(UserMoneyEntity.class, userId);

        password = theUser.getPassword();

        if(password != userMoneyVO.getPassword()){
            return ResultMessage.WRONG_PASSWORD;

        }


        return ResultMessage.SUCCESS;
    }

    public ResultMessage updatePayMoney(String userName, double orderMoney) {
        Session session = getCurrentSession();
        UserMoneyEntity theUser = new UserMoneyEntity();
        theUser = baseDAO.getEntity(UserMoneyEntity.class, userName);
        double primaryMoney = theUser.getMoney();
        double newMoney = primaryMoney - orderMoney;

        theUser.setMoney(newMoney);

        ResultMessage result = baseDAO.saveOrUpdate(theUser);



        return result;
    }

    public ResultMessage confirmOrderPay(int orderId) {
        ResultMessage result = null;

        TicketOrderEntity theOrder = new TicketOrderEntity();
        theOrder = baseDAO.getEntity(TicketOrderEntity.class, orderId);

        theOrder.setOrderType(1); //确认支付

        result = baseDAO.saveOrUpdate(theOrder);

        return result;
    }
}
