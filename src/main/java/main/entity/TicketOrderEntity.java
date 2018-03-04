package main.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by liyipeng on 2018/3/4.
 */
@Entity
@Table(name = "ticketOrder", schema = "XiaoMai")
public class TicketOrderEntity {
    private int orderId;
    private String userId;
    private Integer performId;
    private Integer ticketNum;
    private Integer ticketMoney;
    private Double orderMoney;
    private Timestamp orderTime;
    private Integer orderType;
    private Integer couponId;
    private Timestamp shouldPay;
    private Double backMoney;

    @Id
    @Column(name = "orderId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "performId")
    public Integer getPerformId() {
        return performId;
    }

    public void setPerformId(Integer performId) {
        this.performId = performId;
    }

    @Basic
    @Column(name = "ticketNum")
    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    @Basic
    @Column(name = "ticketMoney")
    public Integer getTicketMoney() {
        return ticketMoney;
    }

    public void setTicketMoney(Integer ticketMoney) {
        this.ticketMoney = ticketMoney;
    }

    @Basic
    @Column(name = "orderMoney")
    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    @Basic
    @Column(name = "orderTime")
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Basic
    @Column(name = "orderType")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "couponId")
    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    @Basic
    @Column(name = "shouldPay")
    public Timestamp getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(Timestamp shouldPay) {
        this.shouldPay = shouldPay;
    }

    @Basic
    @Column(name = "backMoney")
    public Double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(Double backMoney) {
        this.backMoney = backMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketOrderEntity that = (TicketOrderEntity) o;

        if (orderId != that.orderId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (performId != null ? !performId.equals(that.performId) : that.performId != null) return false;
        if (ticketNum != null ? !ticketNum.equals(that.ticketNum) : that.ticketNum != null) return false;
        if (ticketMoney != null ? !ticketMoney.equals(that.ticketMoney) : that.ticketMoney != null) return false;
        if (orderMoney != null ? !orderMoney.equals(that.orderMoney) : that.orderMoney != null) return false;
        if (orderTime != null ? !orderTime.equals(that.orderTime) : that.orderTime != null) return false;
        if (orderType != null ? !orderType.equals(that.orderType) : that.orderType != null) return false;
        if (couponId != null ? !couponId.equals(that.couponId) : that.couponId != null) return false;
        if (shouldPay != null ? !shouldPay.equals(that.shouldPay) : that.shouldPay != null) return false;
        if (backMoney != null ? !backMoney.equals(that.backMoney) : that.backMoney != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (performId != null ? performId.hashCode() : 0);
        result = 31 * result + (ticketNum != null ? ticketNum.hashCode() : 0);
        result = 31 * result + (ticketMoney != null ? ticketMoney.hashCode() : 0);
        result = 31 * result + (orderMoney != null ? orderMoney.hashCode() : 0);
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        result = 31 * result + (couponId != null ? couponId.hashCode() : 0);
        result = 31 * result + (shouldPay != null ? shouldPay.hashCode() : 0);
        result = 31 * result + (backMoney != null ? backMoney.hashCode() : 0);
        return result;
    }
}
