package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.OrderDAO;
import main.entity.TicketOrderEntity;
import main.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by liyipeng on 2018/3/1.
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    BaseDAO baseDAO;



    public ResultMessage createOrder(TicketOrderEntity orderEntity) {
        try {
            int resultId = (Integer) baseDAO.save(orderEntity);
            System.out.println("look resultId: " + resultId);
            if(resultId >0){
                return ResultMessage.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.FAILURE;
    }
}
