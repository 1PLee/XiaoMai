package main.util;


import main.dao.BaseDAO;
import main.dao.UserOrderDAO;
import main.entity.TicketOrderEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/3/6.
 */

public class TimeAction {

    @Autowired
    UserOrderDAO orderDAO;

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }

    @Transactional
    public void checkInvalidOrder(){ //检查超过15分钟未付款的订单设为无效状态
        Session session = getCurrentSession();
        List<TicketOrderEntity> unPayOrders = new ArrayList<TicketOrderEntity>();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        unPayOrders = session.createQuery(
                "from TicketOrderEntity " +
                        "where orderType =:unPayType"
        )
                .setParameter("unPayType", 0)
                .list();

        Iterator<TicketOrderEntity> iterator = unPayOrders.iterator();

        TicketOrderEntity oneOrder = null;

        while (iterator.hasNext()){
            oneOrder = iterator.next();
            if(oneOrder.getShouldPay().before(now)){ //已经过了15分钟期限
                oneOrder.setOrderType(3);
                baseDAO.saveOrUpdate(oneOrder);
            }

        }

        System.out.println("未付款订单:"+unPayOrders.size());

    }


}
