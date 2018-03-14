package main.util;

import main.dao.BaseDAO;
import main.dao.PerformDAO;
import main.entity.PerformEntity;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyipeng on 2018/3/13.
 */
public class TimeAction2 {

    @Autowired
    BaseDAO baseDAO;

    @Autowired
    PerformDAO performDAO;

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        return session;
    }

    @Transactional
    public void checkPerformState(){ //每天定时检查演出状态 判断演出是否结束
        Session session = getCurrentSession();
        int updateEntities = 0;

        Date now = new Date();
        boolean isEnd = false;

        ScrollableResults unHappenPerform = session.createQuery(
                "from PerformEntity " +
                        "where state = 1"
        )
                .setCacheMode(CacheMode.IGNORE)
                .scroll(ScrollMode.FORWARD_ONLY);

        int count = 0;
        String performTime = null;

        while (unHappenPerform.next()){
            PerformEntity onePerform = (PerformEntity) unHappenPerform.get(0);
            performTime = onePerform.getTime();

            isEnd = DateUtil.dateCompare(now, performTime);
            if(isEnd){ //演出已经结束
                onePerform.setState(2);
                count++;
            }

            if (count % 20 == 0) {
                session.flush();
                session.clear();
            }

        }

        System.out.println("执行更新操作");

    }

}
