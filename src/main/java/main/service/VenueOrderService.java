package main.service;

import main.util.ResultMessage;
import main.vo.CreateOrderResultVO;
import main.vo.OrderVO;

/**
 * Created by liyipeng on 2018/3/13.
 */
public interface VenueOrderService {

    CreateOrderResultVO buyTicketOnSite(OrderVO orderVO); //场馆管理人员在现场为用户购票

    CreateOrderResultVO managerCreateOrder(OrderVO orderVO); //场馆管理人员创建订单

}
