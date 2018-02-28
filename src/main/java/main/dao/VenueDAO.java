package main.dao;

import main.entity.VenueEntity;

/**
 * Created by liyipeng on 2018/2/27.
 */
public interface VenueDAO {

    VenueEntity getVenueInfo(String venue);
}
