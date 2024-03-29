package main.util;

/**
 * Created by liyipeng on 2018/2/14.
 */
public enum  ResultMessage {



    SUCCESS("操作成功"),

    FAILURE("操作失败"),

    FAILURE_WRONGMAILCODE("邮箱激活码验证错误"),

    FAILURE_MAILREPEATED("邮箱已经绑定过账户"),

    FAILURE_COUPONUPDATE("优惠券使用失败"),

    FAILURE_NONESEAT("座位已全部订完！"),

    FAILURE_HASCHECKED("票已经检查过"),

    FAILURE_TIMEEARLY("演出时间未到无法检票"),

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
