package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.ManagerDAO;
import main.entity.VenueEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyipeng on 2018/3/5.
 */
@Repository
public class ManagerDAOImpl implements ManagerDAO {

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }


    public List<VenueEntity> showVenueList(int type) {
        Session session = getCurrentSession();
        List<VenueEntity> venueEntityList = new ArrayList<VenueEntity>();

        venueEntityList = session.createQuery(
                "from VenueEntity " +
                        "where type = :queryType"
        )
                .setParameter("queryType", type)
                .list();


        return venueEntityList;
    }
}
