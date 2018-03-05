package main.service.Impl;

import main.dao.VenueDAO;
import main.entity.VenueEntity;
import main.service.VenueService;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liyipeng on 2018/2/27.
 */
@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    VenueDAO venueDAO;


    @Transactional
    public VenueVO getVenueInfo(String venue) {
        VenueEntity venueEntity = new VenueEntity();
        venueEntity = venueDAO.getVenueInfo(venue);

        VenueVO theVenue = new VenueVO();
        theVenue.setDescription(venueEntity.getDescription());
        theVenue.setLocation(venueEntity.getLocation());
        theVenue.setCapacity(venueEntity.getCapacity());

        return theVenue;
    }
}
