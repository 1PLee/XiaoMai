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
    private int mailState; //0代表未激活邮箱 1代表已激活
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
    @Column(name = "mailState")
    public int getMailState() {
        return mailState;
    }

    public void setMailState(int mailState) {
        this.mailState = mailState;
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
}
