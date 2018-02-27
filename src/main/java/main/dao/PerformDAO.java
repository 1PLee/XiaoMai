package main.dao;

import main.entity.DescriptionEntity;
import main.entity.PerformEntity;
import main.entity.PriceEntity;
import main.entity.VenueEntity;

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


}
