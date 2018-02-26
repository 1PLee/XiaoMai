package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.entity.PerformEntity;
import main.entity.PriceEntity;
import main.entity.VenueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/12.
 */
@Repository
public class PerformDAOImpl implements PerformDAO {

    @Autowired
    BaseDAO baseDAO;


    public List<PerformEntity> getAllPerform() {


        return baseDAO.getAll(PerformEntity.class);
    }

/*    public List<PriceEntity> getAllPrice() {

        return baseDAO.getAll(PriceEntity.class);
    }*/

    public List<VenueEntity> getAllVenue() {

        return baseDAO.getAll(VenueEntity.class);
    }

    public List<Object[]> getPrice(int performID) {
        String hql = "select priceOne,priceTwo,priceThree,priceFour,priceFive,priceSix  " +
                "from PriceEntity where performId = "+performID;
        List<Object[]> allPrice = new ArrayList<Object[]>();

        allPrice = baseDAO.getByHql(hql);

        return allPrice;
    }
}
