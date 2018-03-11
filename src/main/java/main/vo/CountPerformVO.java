package main.vo;

/**
 * Created by liyipeng on 2018/3/11.
 * 用来展示场馆承办的演出信息
 */
public class CountPerformVO {
    int performId;
    String performName;
    String performTime;
    int performType; //演出类型 (演唱会 音乐会 话剧 舞蹈 体育比赛)

    int sellTickets; //卖出的座位数
    double totalIncome; // 演出总收入

    public int getPerformId() {
        return performId;
    }

    public void setPerformId(int performId) {
        this.performId = performId;
    }

    public String getPerformName() {
        return performName;
    }

    public void setPerformName(String performName) {
        this.performName = performName;
    }

    public String getPerformTime() {
        return performTime;
    }

    public void setPerformTime(String performTime) {
        this.performTime = performTime;
    }

    public int getPerformType() {
        return performType;
    }

    public void setPerformType(int performType) {
        this.performType = performType;
    }

    public int getSellTickets() {
        return sellTickets;
    }

    public void setSellTickets(int sellTickets) {
        this.sellTickets = sellTickets;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
    
}
