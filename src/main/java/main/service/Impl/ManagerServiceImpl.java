package main.service.Impl;

import main.dao.ManagerDAO;
import main.entity.VenueEntity;
import main.service.ManagerService;
import main.vo.VenueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDAO managerDAO;


    @Transactional
    public List<VenueVO> showVenueList(int type) {
        List<VenueEntity> venueEntityList = new ArrayList<VenueEntity>();
        List<VenueVO> venueVOList = new ArrayList<VenueVO>();

        venueEntityList = managerDAO.showVenueList(type);
        Iterator<VenueEntity> iterator = venueEntityList.iterator();

        VenueEntity oneVenueEntity = null;
        VenueVO oneVenueVO = null;

        while (iterator.hasNext()){
            oneVenueEntity = iterator.next();
            oneVenueVO = new VenueVO();

            oneVenueVO.setVenueID(oneVenueEntity.getVenueId());
            oneVenueVO.setName(oneVenueEntity.getVenue());
            oneVenueVO.setLocation(oneVenueEntity.getLocation());
            oneVenueVO.setDescription(oneVenueEntity.getDescription());
            oneVenueVO.setCapacity(oneVenueEntity.getCapacity());
            oneVenueVO.setType(oneVenueEntity.getType());
            oneVenueVO.setMail(oneVenueEntity.getMail());
            oneVenueVO.setCode(oneVenueEntity.getCode());

            venueVOList.add(oneVenueVO);
        }

        return venueVOList;
    }


}
