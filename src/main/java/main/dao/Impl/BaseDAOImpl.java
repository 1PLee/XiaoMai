package main.dao.Impl;

import main.dao.BaseDAO;
import main.util.ResultMessage;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by liyipeng on 2018/2/13.
 */
@Repository
public class BaseDAOImpl implements BaseDAO {

    @Autowired
    protected SessionFactory sessionFactory;

    private Session getNewSession(){

        return sessionFactory.openSession();
    }
    private Session getCurrentSession(){
        Session session=sessionFactory.getCurrentSession();

        //return session==null?getNewSession():session;
        return session;
    }


    public int save(Object entity) throws Exception {
        Session session= getCurrentSession();
        Transaction tr=session.beginTransaction();
        try {
            int id=(Integer) session.save(entity);
            return id;
        }catch (Exception e){
            throw e;
        }finally {
            tr.commit();
/*            session.clear();
            session.close();*/
        }
    }

    public ResultMessage saveOrUpdate(Object entity) {
        Session session= getCurrentSession();
        Transaction tr=session.beginTransaction();
        try {
            session.saveOrUpdate(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.FAILURE;
        }finally {
            tr.commit();
/*            session.clear();
            session.close();*/
        }
        return ResultMessage.SUCCESS;
    }

    public ResultMessage update(Object entity) {
        Session session= getCurrentSession();
        Transaction tr=session.beginTransaction();
        try {
            session.merge(entity);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMessage.FAILURE;
        }finally {
            tr.commit();
/*            session.clear();
            session.close();*/
        }
        return ResultMessage.SUCCESS;
    }

    public <T> T getEntity(Class<T> c, int id) {
        Session session = sessionFactory.getCurrentSession();
/*        if (session != null) {
            session.clear(); // internal cache clear
        }*/

/*        Cache cache = sessionFactory.getCache();

        if (cache != null) {
            cache.evictAllRegions(); // Evict data from all query regions.
            cache.evictCollectionRegions();
            cache.evictDefaultQueryRegion();
            cache.evictEntityRegions();
            cache.evictQueryRegions();
            cache.evictNaturalIdRegions();
        }*/

        T entity=session.get(c,id);
        return entity;
    }


}
