package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/3/4.
 */
@Entity
@Table(name = "userMoney", schema = "XiaoMai")
public class UserMoneyEntity {
    private String userId;
    private Double money;
    private int password;

    @Id
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "money")
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Basic
    @Column(name = "password")
    public int getPassword(){
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMoneyEntity that = (UserMoneyEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (money != null ? !money.equals(that.money) : that.money != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (money != null ? money.hashCode() : 0);
        return result;
    }
}
