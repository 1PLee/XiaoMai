package main.vo;

/**
 * Created by liyipeng on 2018/2/20.
 */
public class UserVO {
    public String id;
    public int password;
    public String mail;
    public int mailState; //0代表未激活 1代表已激活
    public int vipGrade; // 0 代表白银 1代表黄金 2代表钻石
    public int vipIsStop; //1 取消会员 0 未取消
    public int vipScore; //会员积分
    public int mailCode;//邮箱激活码

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPassword() {
        return password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMailState(int mailState) {
        this.mailState = mailState;
    }

    public int getMailState() {
        return mailState;
    }

    public void setVipGrade(int vipGrade) {
        this.vipGrade = vipGrade;
    }

    public int getVipGrade() {
        return vipGrade;
    }

    public void setVipIsStop(int vipIsStop) {
        this.vipIsStop = vipIsStop;
    }

    public int getVipIsStop() {
        return vipIsStop;
    }

    public void setVipScore(int vipScore) {
        this.vipScore = vipScore;
    }

    public int getVipScore() {
        return vipScore;
    }

    public void setMailCode(int mailCode) {
        this.mailCode = mailCode;
    }

    public int getMailCode() {
        return mailCode;
    }
}
