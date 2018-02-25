package main.dao;

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

    List<PriceEntity> getPrice(int performID);
}
