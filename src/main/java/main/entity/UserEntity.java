package main.entity;

import javax.persistence.*;

/**
 * Created by liyipeng on 2018/2/20.
 */
@Entity
@Table(name = "user", schema = "XiaoMai")
public class UserEntity {
    private String id;
    private int password;
    private String mail;
    private int vipGrade; //0 白银  1 黄金  2 钻石
    private int vipIsStop; //1 取消会员 0 未取消
    private int vipScore; //会员积分


    @Id
    @Column(name = "id")
    public String getId(){
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "password")
    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    @Basic
    @Column(name = "vipGrade")
    public int getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(int vipGrade) {
        this.vipGrade = vipGrade;
    }

    @Basic
    @Column(name = "vipIsStop")
    public int getVipIsStop() {
        return vipIsStop;
    }

    public void setVipIsStop(int vipIsStop) {
        this.vipIsStop = vipIsStop;
    }

    @Basic
    @Column(name = "vipScore")
    public int getVipScore() {
        return vipScore;
    }

    public void setVipScore(int vipScore) {
        this.vipScore = vipScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (password != that.password) return false;
        if (vipGrade != that.vipGrade) return false;
        if (vipIsStop != that.vipIsStop) return false;
        if (vipScore != that.vipScore) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + password;
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + vipGrade;
        result = 31 * result + vipIsStop;
        result = 31 * result + vipScore;
        return result;
    }
}
