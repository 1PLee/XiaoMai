package main.service.Impl;

import main.dao.PerformDAO;
import main.entity.PerformEntity;
import main.entity.PriceEntity;
import main.entity.VenueEntity;
import main.service.PerformService;
import main.vo.PerformVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */

@Service
public class PerformServiceImpl implements PerformService {
    //private static final Logger log = LoggerFactory.getLogger(PerformServiceBean.class);
    @Autowired
    PerformDAO performDAO;

    @Transactional
    public List<PerformVO> getAllPerforms() {

        List<PerformEntity> performList = performDAO.getAllPerform();


        List<PerformVO> performVOList = new ArrayList<PerformVO>();

        Iterator<PerformEntity> iterator = performList.iterator();

        PerformEntity onePerform = null;
        PerformVO onePerformVO = null;

        while (iterator.hasNext()){
            onePerform = iterator.next();
            onePerformVO = new PerformVO();
            onePerformVO.setName(onePerform.getName());
            onePerformVO.setPerformID(onePerform.getId());
            onePerformVO.setTime(onePerform.getTime());
            onePerformVO.setPriceMin(onePerform.getPriceMin());
            onePerformVO.setVenue(onePerform.getAddress());
            if(onePerform.getType() == 1){
                onePerformVO.setType("演唱会");
            }else if(onePerform.getType() == 2) {
                onePerformVO.setType("音乐会");
            }
            performVOList.add(onePerformVO);
        }


        return performVOList;
    }


    /*根据performID 得到演出的全部价格*/
    public List<Integer> getPrice(int performID) {
        List<Object[]> performPriceList = new ArrayList<Object[]>();
        performPriceList = performDAO.getPrice(performID);

        Object[] performPrice = performPriceList.get(0);

        List<Integer> priceList = new ArrayList<Integer>(); //这个perform的全部票价

        for (int i = 0;i<performPrice.length;i++){
                priceList.add((Integer) performPrice[i]);
        }

        return priceList;
    }

    /*根据performID 得到演出的全部座位情况*/
    public List<Integer> getSeat(int performID) {
        List<Object[]> performSeatList = new ArrayList<Object[]>();
        performSeatList = performDAO.getSeat(performID);

        Object[] performSeat = performSeatList.get(0);

        List<Integer> seatList = new ArrayList<Integer>();

        for(int i = 0;i < performSeat.length;i++){
            seatList.add((Integer)performSeat[i]);
        }

        return seatList;
    }

    @Transactional
    public PerformVO getPerformInfo(int performID) {
        PerformVO thePerform = new PerformVO();
        List<Integer> priceList = new ArrayList<Integer>();
        List<Integer> seatList = new ArrayList<Integer>();

        priceList = getPrice(performID);
        seatList = getSeat(performID);

        thePerform.setPrice(priceList);
        thePerform.setSeat(seatList);

        return thePerform;
    }

}

