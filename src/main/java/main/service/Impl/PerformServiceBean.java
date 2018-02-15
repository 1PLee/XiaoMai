package main.service.Impl;

import main.dao.PerformDAO;
import main.entity.PerformEntity;
import main.entity.PriceEntity;
import main.entity.VenueEntity;
import main.service.PerformService;
import main.vo.PerformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
@Transactional
@Service
public class PerformServiceBean implements PerformService {
    @Autowired
    PerformDAO performDAO;

    public List<PerformVO> getAllPerforms() {
        List<PerformEntity> performList = performDAO.getAllPerform();
        List<PriceEntity> priceList = performDAO.getAllPrice();
        List<VenueEntity> venueList = performDAO.getAllVenue();

        System.out.println(performList.size());

        return null;
    }
}

