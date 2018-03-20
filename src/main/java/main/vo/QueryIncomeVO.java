package main.vo;

import main.util.ResultMessage;

/**
 * Created by liyipeng on 2018/3/20.
 */
public class QueryIncomeVO { //查询场馆某一年的收入情况

    ResultMessage queryResult;
    double income;

    public ResultMessage getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(ResultMessage queryResult) {
        this.queryResult = queryResult;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
