package main.util;

/**
 * Created by liyipeng on 2018/2/14.
 */
public enum  ResultMessage {



    SUCCESS("操作成功"),

    FAILURE("操作失败"),

    FAILURE_WRONGMAILCODE("邮箱激活码验证错误"),

    FAILURE_MAILREPEATED("邮箱已经绑定过账户"),

    NONE_USER("用户不存在"),

    WRONG_PASSWORD("密码错误"),

    USER_REPEATED("用户名已存在"),

    VIP_1("白银会员"),

    VIP_2("黄金会员"),

    VIP_3("钻石会员");



    private String indicate;

    private ResultMessage(String indicate){
        this.indicate = indicate;
    }

    public String toShow(){
        return this.indicate;
    }

}
