package main.dao;

import main.entity.VenueEntity;

import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
public interface ManagerDAO {

    List<VenueEntity> showVenueList(int type);


}
