package main.dao;

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
}
