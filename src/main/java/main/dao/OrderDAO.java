package main.dao;

import main.entity.TicketOrderEntity;
import main.util.ResultMessage;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface OrderDAO {

    ResultMessage createOrder(TicketOrderEntity orderEntity);
}
