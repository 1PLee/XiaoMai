package main.service.Impl;

import com.sun.corba.se.spi.ior.ObjectKey;
import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.dao.VenueDAO;
import main.entity.PerformEntity;
import main.entity.VenueEntity;
import main.service.UserOrderService;
import main.service.VenueService;
import main.util.DateUtil;
import main.util.ResultMessage;
import main.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/27.
 */
@Service
public class VenueServiceImpl implements VenueService {
    @Autowired
    VenueDAO venueDAO;

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    PerformDAO performDAO;

    @Autowired
    UserOrderService userOrderService;


    @Transactional
    public VenueVO getVenueInfo(String venue) {
        VenueEntity venueEntity = new VenueEntity();
        venueEntity = venueDAO.getVenueInfo(venue);

        VenueVO theVenue = new VenueVO();
        theVenue.setDescription(venueEntity.getDescription());
        theVenue.setLocation(venueEntity.getLocation());
        theVenue.setCapacity(venueEntity.getCapacity());
        theVenue.setVenueID(venueEntity.getVenueId());
        theVenue.setMail(venueEntity.getMail());

        return theVenue;
    }


    @Transactional
    public ResultMessage registerVenue(VenueVO venueVO) {
        VenueEntity venueEntity = new VenueEntity();
        venueEntity.setVenue(venueVO.getName());
        venueEntity.setCapacity(venueVO.getCapacity());
        venueEntity.setLocation(venueVO.getLocation());
        venueEntity.setDescription(venueVO.getDescription());
        venueEntity.setMail(venueVO.getMail());
        venueEntity.setType(0);


        ResultMessage result = null;
        result = venueDAO.registerVenue(venueEntity);

        return result;
    }




    @Transactional
    public ResultMessage loginVenue(VenueVO venueVO) {
        VenueEntity theVenue = new VenueEntity();
        String venueName = venueVO.getName();
        int venueCode = venueVO.getCode();

        theVenue = venueDAO.getVenueInfo(venueName);

        if(venueCode != theVenue.getCode()){
            return ResultMessage.WRONG_PASSWORD;
        }

        return ResultMessage.SUCCESS;
    }

    @Transactional
    public List<PerformVO> getOnSellPerforms(String venue) {

        List<PerformEntity> allPerforms = performDAO.getAllPerformByVenue(venue);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp performTime = null;

        List<PerformVO> onSellPerforms = new ArrayList<PerformVO>();
        Object[] performPrice = null;
        Object[] performSeat = null;
        List<Object[] > seatList = null;
        List<Integer> priceList = null;

        PerformVO onePerform = null;

        for (PerformEntity p : allPerforms) {
            performTime = DateUtil.String2Timestamp(p.getTime());

            if(!(performTime.before(now))){ //演出还没有结束

                onePerform = new PerformVO();
                performPrice = performDAO.getPrice(p.getId()).get(0);
                System.out.println("look performId:" + p.getId());
                seatList = performDAO.getSeat(p.getId());

                if(seatList.size() != 0){

                    performSeat = seatList.get(0);
                    priceList = new ArrayList<Integer>();

                    for(int i =0;i<performSeat.length;i++){

                        if((Integer)performSeat[i] != 0){ //还有座位

                            priceList.add((Integer) performPrice[i]);

                        }

                    }

                    onePerform.setPrice(priceList);
                    onePerform.setName(p.getName());
                    onePerform.setPerformID(p.getId());
                    onSellPerforms.add(onePerform);

                }


            }


        }


        return onSellPerforms;
    }


}
