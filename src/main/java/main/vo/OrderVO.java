package main.vo;

import java.sql.Timestamp;

/**
 * Created by liyipeng on 2018/3/3.
 */
public class OrderVO {
    public int orderId;
    public String userId;
    public int performId;
    public int ticketNum;
    public int ticketMoney;
    public double orderMoney;
    public Timestamp orderTime;
    public int orderType;
    public int couponId;
    public Timestamp shouldPay;
    public int ticketSeat;//座位等级


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getTicketMoney() {
        return ticketMoney;
    }

    public void setTicketMoney(int ticketMoney) {
        this.ticketMoney = ticketMoney;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public Timestamp getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Timestamp shouldPay) {
        this.shouldPay = shouldPay;
    }

    public int getTicketSeat() {
        return ticketSeat;
    }

    public void setTicketSeat(int ticketSeat) {
        this.ticketSeat = ticketSeat;
    }
}
