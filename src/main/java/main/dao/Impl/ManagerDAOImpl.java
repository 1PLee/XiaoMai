package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.ManagerDAO;
import main.entity.VenueEntity;
import main.util.ResultMessage;
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

    public ResultMessage checkVenue(String venue, int action, int code) {
        Session session = getCurrentSession();

        if(action == 1){ //审核通过 可以注册识别码
            int updateEntity = 0;
            updateEntity = session.createQuery(
                    "update VenueEntity " +
                            "set type = :newType, code = :newCode " +
                            "where venue = :venueName"
            )
                    .setParameter("newType", 1)
                    .setParameter("newCode", code)
                    .setParameter("venueName", venue)
                    .executeUpdate();

            if(updateEntity != 0){
                return ResultMessage.SUCCESS;
            }

        }else { //审核不通过 删除之
            int deleteEntity = 0;
            deleteEntity = session.createQuery(
                    "delete VenueEntity " +
                            "where venue = :venueName"
            )
                    .setParameter("venueName", venue)
                    .executeUpdate();

            if(deleteEntity != 0){
                return ResultMessage.SUCCESS;
            }

        }

        return ResultMessage.FAILURE;
    }
}
