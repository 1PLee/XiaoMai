package main.dao;

import main.entity.TicketOrderEntity;
import main.entity.VenueEntity;
import main.util.ResultMessage;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerDAO {

    List<VenueEntity> showVenueList(int type);

    ResultMessage checkVenue(String venue, int action, int code); //审批场馆注册申请 如果通过 code为识别码 否则为null

    ResultMessage payVenueIncome(int venueId, double income, int year);

    int countVIPGrade(int grade); //统计各个等级 vip人数 1代表白银 2代表黄金 3代表钻石

    int countVenueByCapacity(int low, int high); //判断场馆容纳量处于的区间

    List<TicketOrderEntity> getYearOrders(String year); //得到某一年的全部订单
}
