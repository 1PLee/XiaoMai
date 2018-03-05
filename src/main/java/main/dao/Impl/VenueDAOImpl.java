package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.VenueDAO;
import main.entity.VenueEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liyipeng on 2018/2/27.
 */
@Repository
public class VenueDAOImpl implements VenueDAO {

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }


    @Transactional
    public VenueEntity getVenueInfo(String venue) {
        VenueEntity theVenue = new VenueEntity();
        Session session = getCurrentSession();
        List<Object[]> venueList = new ArrayList<Object[]>();

        venueList = session.createQuery(
                "select  description, location, capacity " +
                        "from VenueEntity " +
                        "where venue = :venue"
        )
                .setParameter("venue", venue)
                .list();
        Object[] venueInfo = venueList.get(0);

        theVenue.setDescription((String) venueInfo[0]);
        theVenue.setLocation((String) venueInfo[1]);
        theVenue.setCapacity((Integer)venueInfo[2]);


        return theVenue;
    }
}
