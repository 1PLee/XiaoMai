package main.dao.Impl;

import main.dao.BaseDAO;
import main.dao.UserDAO;
import main.entity.UserEntity;
import main.util.ResultMessage;
import org.hibernate.Session;
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

    public ResultMessage register(UserEntity userEntity) throws Exception {

        ResultMessage result = null;
        String id = null;

        id = baseDAO.save(userEntity);
        System.out.println("look the id:"+id);

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


}
