package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.UserDAO;
import main.entity.CouponEntity;
import main.entity.UserEntity;
import main.util.ResultMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/20.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }

    public ResultMessage register(UserEntity userEntity) throws Exception {

        ResultMessage result = null;
        String id = null;

        id = baseDAO.save(userEntity);

        if(id != null){
            result = ResultMessage.SUCCESS;
        }else {
            result = ResultMessage.FAILURE;
        }

        return result;
    }

    public List<String> getAllUserID() {
        List<String> userID = new ArrayList<String>();
        userID = baseDAO.getByHql("select id from UserEntity ");

        return userID;
    }

    public List<String> getAllMail() {
        List<String> userMail = new ArrayList<String>();
        userMail = baseDAO.getByHql("select mail from UserEntity");

        return userMail;
    }

    public UserEntity getUserInfo(String userId) {
        UserEntity theUser = new UserEntity();
        theUser = baseDAO.getEntity(UserEntity.class, userId);

        return theUser;
    }


    public ResultMessage cancelVIP(String userId) {
        ResultMessage result = null;

        Session session = getCurrentSession();
        int updatedEntities = 0;
        updatedEntities = session.createQuery(
                "update UserEntity " +
                        "set vipIsStop = :isStop " +
                        "where id = :userId"

        ).setParameter("isStop", 1)
                .setParameter("userId", userId)
                .executeUpdate();

        if(updatedEntities != 0){
            result = ResultMessage.SUCCESS;
        }else {
            result = ResultMessage.FAILURE;
        }

        return result;
    }

    public ResultMessage changePasswd(String userId, int newPasswd) {

        Session session = getCurrentSession();
        int updatedEntities = 0;
        updatedEntities = session.createQuery(
                "update UserEntity " +
                        "set password = :password " +
                        "where id = :userId"
        )
                .setParameter("password", newPasswd)
                .setParameter("userId", userId)
                .executeUpdate();

        if(updatedEntities != 0){
            return ResultMessage.SUCCESS;
        }

        return ResultMessage.FAILURE;
    }

    public List<CouponEntity> getCoupon(String userId) {
        List<CouponEntity> allCoupon = new ArrayList<CouponEntity>();

        Session session = getCurrentSession();
        allCoupon = session.createQuery(
                "from CouponEntity " +
                        "where userId = :userId"
        )
                .setParameter("userId", userId)
                .list();


        return allCoupon;
    }


    public ResultMessage convertCoupon(CouponEntity couponEntity) {
        try {
            int couponID = (Integer) baseDAO.save(couponEntity);
            if(couponID != 0){
                return ResultMessage.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.FAILURE;
    }

    public ResultMessage updateScore(String userId, int needScore) {
        ResultMessage result = null;
        UserEntity theUser = baseDAO.getEntity(UserEntity.class, userId);
        int primaryScore = theUser.getVipScore();
        int userScore = primaryScore - needScore;
        System.out.println("update Score:  " + userScore);
        theUser.setVipScore(userScore);
        result = baseDAO.saveOrUpdate(theUser);

        return result;
    }

    public ResultMessage updateCouponState(String userId, int couponId) {
        ResultMessage result = null;
        int updatedEntities = 0;
        Session session = getCurrentSession();

        updatedEntities = session.createQuery(
                "update CouponEntity " +
                        "set isUse = :useState " +
                        "where userId = :userId and couponId = :couponId"
        )
                .setParameter("useState", 1)
                .setParameter("userId", userId)
                .setParameter("couponId", couponId)
                .executeUpdate();

        if(updatedEntities == 0){
            return ResultMessage.FAILURE;
        }

        return ResultMessage.SUCCESS;
    }


}
