package main.util;

/**
 * Created by liyipeng on 2018/2/14.
 */
public enum  ResultMessage {



    SUCCESS("操作成功"),

    FAILURE("操作失败");

    private String indicate;

    private ResultMessage(String indicate){
        this.indicate = indicate;
    }

    public String toShow(){
        return this.indicate;
    }

}
