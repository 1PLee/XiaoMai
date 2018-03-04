package main.dao;

import main.entity.*;
import main.util.ResultMessage;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
public interface PerformDAO {

    List<PerformEntity> getAllPerform();

//    List<PriceEntity> getAllPrice();

    List<VenueEntity> getAllVenue();

    List<Object[]> getPrice(int performID); //定义为list因为查询默认返回list

    List<Object[]> getSeat(int performID);

    DescriptionEntity getDescription(int performID);

    ResultMessage updatePerformSeat(int performID, int seatGrade, int seatNum ,int type); //下单时表演对应的座位更改, type为1代表加 0代表减


}
