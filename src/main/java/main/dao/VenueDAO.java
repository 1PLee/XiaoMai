package main.dao;

import main.entity.VenueEntity;
import main.entity.VenueIncomeEntity;
import main.util.ResultMessage;

/**
 * Created by liyipeng on 2018/2/27.
 */
public interface VenueDAO {

    VenueEntity getVenueInfo(String venue);

    ResultMessage registerVenue(VenueEntity venueEntity);

    VenueIncomeEntity queryVenueIncome(int venueId, int year);

}
