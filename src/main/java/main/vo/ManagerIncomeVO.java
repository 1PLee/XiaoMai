package main.vo;

/**
 * Created by liyipeng on 2018/3/16.
 * 网站的财务信息统计(以月为单位)
 */
public class ManagerIncomeVO {

    public double totalIncome; //该月的总收入
    public double backMoney;//在该月下的订单但是选择退款的总金额
    public double totalProfit; // 该月的净利润（暂时为总收入减去退款总金额）

    public int performs; // 该月举办的演出场次

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(double backMoney) {
        this.backMoney = backMoney;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getPerforms() {
        return performs;
    }

    public void setPerforms(int performs) {
        this.performs = performs;
    }

}
