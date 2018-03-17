package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.ManagerDAO;
import main.entity.TicketOrderEntity;
import main.entity.VenueEntity;
import main.entity.VenueIncomeEntity;
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

    public ResultMessage payVenueIncome(int venueId, double income, int year) {

        ResultMessage result = null;
        Session session = getCurrentSession();

        VenueIncomeEntity venueIncomeEntity = new VenueIncomeEntity();

        venueIncomeEntity = (VenueIncomeEntity) session.createQuery(
                "from VenueIncomeEntity " +
                        "where venueId = :venueId and year = :year"
        )
                .setParameter("venueId", venueId)
                .setParameter("year", year)
                .uniqueResult();

        if(venueIncomeEntity != null){
            double venueIncome = venueIncomeEntity.getIncome();
            venueIncome = venueIncome + income;
            venueIncomeEntity.setIncome(venueIncome);
        }else {
            venueIncomeEntity.setVenueId(venueId);
            venueIncomeEntity.setYear(year);
            venueIncomeEntity.setIncome(income);
        }

        result = baseDAO.saveOrUpdate(venueIncomeEntity);

        return result;
    }

    public int countVIPGrade(int grade) {
        Session session = getCurrentSession();

        List<Long> longList = new ArrayList<Long>();
        long countLong = 0;



        longList = session.createQuery(
                "select count(vipGrade) " +
                        "from UserEntity " +
                        "where vipGrade = :vipGrade"
        )
                .setParameter("vipGrade", grade)
                .list();


        countLong = longList.get(0);

        int vipNum = 0;
        vipNum = (int) countLong;


        return vipNum;
    }


    public int countVenueByCapacity(int low, int high) {
        Session session = getCurrentSession();

        List<Long> longList = new ArrayList<Long>();
        long countLong = 0;

        longList = session.createQuery(
                "select count(*) " +
                        "from VenueEntity " +
                        "where capacity <(:high) and capacity >(:low)"
        )
                .setParameter("high", high)
                .setParameter("low", low)
                .list();

        countLong = longList.get(0);

        int venueCount = (int) countLong;


        return venueCount;
    }

    public List<TicketOrderEntity> getYearOrders(String year) {
        List<TicketOrderEntity> oneYearOrders = new ArrayList<TicketOrderEntity>();
        Session session = getCurrentSession();

        oneYearOrders = session.createQuery(
                "from TicketOrderEntity " +
                        "where date_format(orderTime, '%x')=(:checkYear)"
        )
                .setParameter("checkYear", year)
                .list();



        return oneYearOrders;
    }
}
