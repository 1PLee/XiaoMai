package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.VenueDAO;
import main.entity.VenueEntity;
import main.util.ResultMessage;
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



    public VenueEntity getVenueInfo(String venue) {
        VenueEntity theVenue = new VenueEntity();
        Session session = getCurrentSession();
        List<Object[]> venueList = new ArrayList<Object[]>();

        venueList = session.createQuery(
                "select  description, location, capacity, venueId, mail " +
                        "from VenueEntity " +
                        "where venue = :venue"
        )
                .setParameter("venue", venue)
                .list();
        Object[] venueInfo = venueList.get(0);

        theVenue.setDescription((String) venueInfo[0]);
        theVenue.setLocation((String) venueInfo[1]);
        theVenue.setCapacity((Integer)venueInfo[2]);
        theVenue.setVenueId((Integer)venueInfo[3]);
        theVenue.setMail((String)venueInfo[4]);


        return theVenue;
    }

    public ResultMessage registerVenue(VenueEntity venueEntity) {
        int venueId = 0;

        try {
            venueId = baseDAO.save(venueEntity);
            if(venueId != 0){

                return ResultMessage.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultMessage.FAILURE;
    }


}
