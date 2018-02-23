package main.dao.Impl;

import main.dao.BaseDAO;
import main.entity.VenueEntity;
import main.util.ResultMessage;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

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


    public <T> T save(Object entity) throws Exception {
        Session session= getCurrentSession();
        //Transaction tr=session.beginTransaction();
        try {
            T id= (T) session.save(entity);
            return id;
        }catch (Exception e){
            throw e;
        }finally {
            //tr.commit();
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

    public <T> List<T> getByHql(String hql) {
        Session session = getCurrentSession();
        return session.createQuery(hql).list();
    }

    public <T> T getEntity(Class<T> c, Serializable id) {
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
        System.out.println("this baseDao id:"+id);
        T entity = session.get(c,id);
        return entity;
    }

    public <T> T loadProxy(Class<T> c, String id) {
        Session session= getCurrentSession();
        T entity=session.load(c,id);
        return entity;
    }

    public <T> List<T> getAll(Class<T> c) {
        Session session = getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(c);

        Root<T> root = query.from(c);

        query.select(root);
        Query<T> q = session.createQuery(query);

        List<T> list = q.getResultList();

        return list;
    }




}
