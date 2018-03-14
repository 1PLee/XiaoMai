package main.vo;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/13.
 *
 * 一场演出具体的统计信息
 */
public class PerformIncomeVO {
    public int performId;
    public double totalIncome; //演出总收入
    public int totalTicketNum; //共卖出多少张票
    public List<Integer> seatCountList; //各个位置的票各卖了多少张
    public int backTicketNum; //退订总票数

    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getBackTicketNum() {
        return backTicketNum;
    }
    public void setBackTicketNum(int backTicketNum) {
        this.backTicketNum = backTicketNum;
    }

    public int getTotalTicketNum() {
        return totalTicketNum;
    }
    public void setTotalTicketNum(int totalTicketNum) {
        this.totalTicketNum = totalTicketNum;
    }

    public List<Integer> getSeatCountList() {
        return seatCountList;
    }
    public void setSeatCountList(List<Integer> seatCountList) {
        this.seatCountList = seatCountList;
    }
}
