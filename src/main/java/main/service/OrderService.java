package main.service;

import main.util.ResultMessage;
import main.vo.OrderVO;

/**
 * Created by liyipeng on 2018/3/1.
 */
public interface OrderService {

    ResultMessage createOrder(OrderVO orderVO);
}
