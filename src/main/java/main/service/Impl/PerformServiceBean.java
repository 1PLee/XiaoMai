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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
@Transactional
@Service
public class PerformServiceBean implements PerformService {
    //private static final Logger log = LoggerFactory.getLogger(PerformServiceBean.class);
    @Autowired
    PerformDAO performDAO;

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

}

