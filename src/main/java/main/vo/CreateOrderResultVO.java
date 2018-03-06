package main.vo;

import main.util.ResultMessage;

/**
 * Created by liyipeng on 2018/3/6.
 */
public class CreateOrderResultVO {
    public ResultMessage result;
    public int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ResultMessage getResult() {
        return result;
    }

    public void setResult(ResultMessage result) {
        this.result = result;
    }

}
