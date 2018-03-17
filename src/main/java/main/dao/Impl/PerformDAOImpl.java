package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.entity.*;
import main.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    @Autowired
    protected SessionFactory sessionFactory;

    private String hqlSeat1 = "update SeatEntity  set seatOne = :seatNum  where performId = :performId ";
    private String hqlSeat2 = "update SeatEntity  set seatTwo = :seatNum  where performId = :performId";
    private String hqlSeat3 = "update SeatEntity  set seatThree = :seatNum  where performId = :performId";
    private String hqlSeat4 = "update SeatEntity  set seatFour = :seatNum  where performId = :performId";
    private String hqlSeat5 = "update SeatEntity  set seatFive = :seatNum  where performId = :performId";
    private String hqlSeat6 = "update SeatEntity  set seatSix = :seatNum  where performId = :performId";


    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }


    public List<PerformEntity> getAllPerform() {


        return baseDAO.getAll(PerformEntity.class);
    }

/*    public List<PriceEntity> getAllPrice() {

        return baseDAO.getAll(PriceEntity.class);
    }*/

    public List<VenueEntity> getAllVenue() {

        return baseDAO.getAll(VenueEntity.class);
    }

    public List<PerformEntity> getPerformsByYear(String year) {
        List<PerformEntity> oneYearPerforms = new ArrayList<PerformEntity>();

        Session session = getCurrentSession();

        oneYearPerforms = session.createQuery(
                "from PerformEntity " +
                        "where time like (:queryYear)"
        )
                .setParameter("queryYear", year+'%')
                .list();


        return oneYearPerforms;
    }

    public List<Object[]> getPrice(int performID) {
        String hql = "select priceOne,priceTwo,priceThree,priceFour,priceFive,priceSix  " +
                "from PriceEntity where performId = "+performID;
        List<Object[]> allPrice = new ArrayList<Object[]>();

        allPrice = baseDAO.getByHql(hql);

        return allPrice;
    }

    public List<Object[]> getSeat(int performID) {
        List<Object[]> performSeat = new ArrayList<Object[]>();
        Session session = getCurrentSession();

        performSeat = session.createQuery(
                "select seatOne,seatTwo,seatThree,seatFour,seatFive,seatSix " +
                        "from SeatEntity " +
                        "where performId = :performID"
        )
                .setParameter("performID", performID)
                .list();

        return performSeat;
    }

    public DescriptionEntity getDescription(int performID) {
        DescriptionEntity theDes = new DescriptionEntity();
        theDes = baseDAO.getEntity(DescriptionEntity.class, performID);

        return theDes;
    }

    public synchronized ResultMessage updatePerformSeat(int performID, int seatGrade, int seatNum, int type) { //seatGrade 为1～6
        Session session = getCurrentSession();
        int primarySeatNum = 0;
        int updateSeatNum = 0;
        int updateResult = 0;
        SeatEntity thePerform = new SeatEntity();

        List<Object[]> performSeat = getSeat(performID);
        Object[] seatNumList = performSeat.get(0);



        if(type != 0){ //增加座位
            for(int i =1; i<=seatNumList.length;i++) {
                if(i == seatGrade){
                    seatNumList[i-1] = ((Integer)seatNumList[i-1])+seatNum;
                }
            }

        }else { //减少座位
            for(int i =1; i<=seatNumList.length;i++) {
                if(i == seatGrade){
                    primarySeatNum = (Integer) seatNumList[i-1];
                    if((primarySeatNum - seatNum)<0){
                        return ResultMessage.FAILURE_NONESEAT;
                    }else {
                        seatNumList[i - 1] = ((Integer) seatNumList[i - 1]) - seatNum;
                    }

                }
            }


        }

        updateResult = session.createQuery(
                "update SeatEntity " +
                        "set seatOne = :seatOne, seatTwo = :seatTwo, " +
                        "seatThree = :seatThree, seatFour = :seatFour, " +
                        "seatFive = :seatFive, seatSix = :seatSix " +
                        "where performId = :performId"
        )
                .setParameter("seatOne", seatNumList[0])
                .setParameter("seatTwo", seatNumList[1])
                .setParameter("seatThree", seatNumList[2])
                .setParameter("seatFour", seatNumList[3])
                .setParameter("seatFive", seatNumList[4])
                .setParameter("seatSix", seatNumList[5])
                .setParameter("performId", performID)
                .executeUpdate();



        if(updateResult == 0){
            return ResultMessage.FAILURE;
        }


        return ResultMessage.SUCCESS;

/*        thePerform = (SeatEntity) session.createQuery(
                "from SeatEntity " +
                        "where performId = :performId"
        )
                .setParameter("performId", performID)
                .uniqueResult();

        *//*判断座位是否空余*//*


        switch (seatGrade) {
            case 1:
                primarySeatNum = thePerform.getSeatOne();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat1).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
                break;
            case 2:
                primarySeatNum = thePerform.getSeatTwo();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat2).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
                break;
            case 3:
                primarySeatNum = thePerform.getSeatThree();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat3).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
                break;
            case 4:
                primarySeatNum = thePerform.getSeatFour();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat4).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
                break;
            case 5:
                primarySeatNum = thePerform.getSeatFive();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat5).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
                break;
            default:
                primarySeatNum = thePerform.getSeatSix();
                updateSeatNum = primarySeatNum --;
                updateResult = session.createQuery(hqlSeat6).setParameter("seatNum",updateSeatNum).setParameter("performId",performID).executeUpdate();
        }
        */

    }

    public List<PerformEntity> getAllPerformByVenue(String venue) {
        Session session = getCurrentSession();

        List<PerformEntity> venuePerforms = new ArrayList<PerformEntity>();

        venuePerforms = session.createQuery(
                "from PerformEntity " +
                        "where address = :venue"
        )
                .setParameter("venue", venue)
                .list();


        return venuePerforms;
    }

    public Object[] getPerformIncome(int performId) {
        Session session = getCurrentSession();
        List<Object[]> numAndIncomeList = new ArrayList<Object[]>();

        Object[] numAndIncome = null;

        numAndIncomeList = session.createQuery(
                "select sum(ticketNum), sum(orderMoney) " +
                        "from TicketOrderEntity " +
                        "where perform.id = :performId and (orderType = 1 or orderType = 2)"
        )
                .setParameter("performId", performId)
                .list();

        numAndIncome = numAndIncomeList.get(0);

        return numAndIncome;
    }


    public List<TicketOrderEntity> getPerformIncomeDetail(int performId, Object[] typeList) {
        List<TicketOrderEntity> orderEntities = new ArrayList<TicketOrderEntity>();

        Session session = getCurrentSession();

        orderEntities = session.createQuery(
                "from TicketOrderEntity " +
                        "where perform.id = :performId and orderType in (:typeList)"
        )
                .setParameter("performId", performId)
                .setParameterList("typeList", typeList)
                .list();


        return orderEntities;
    }


    public List<PerformEntity> getAllUnSettlePerform() {

        List<PerformEntity> unSettlePerforms = new ArrayList<PerformEntity>();

        Session session = getCurrentSession();

        unSettlePerforms = session.createQuery(
                "from PerformEntity " +
                        "where state = 2"
        )
                .list();


        return unSettlePerforms;
    }


}
