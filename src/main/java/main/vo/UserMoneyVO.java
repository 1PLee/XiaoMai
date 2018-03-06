package main.vo;

/**
 * Created by liyipeng on 2018/3/6.
 */
public class UserMoneyVO {
    public String userId;
    public int password;
    public double money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
