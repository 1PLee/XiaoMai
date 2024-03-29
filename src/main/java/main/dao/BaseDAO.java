package main.dao;

import main.util.ResultMessage;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyipeng on 2018/2/13.
 */
public interface BaseDAO {

    <T> T save(Object entity) throws Exception;

    ResultMessage saveOrUpdate(Object entity);

    ResultMessage update(Object entity);

    <T> List<T> getByHql(String hql);

    <T> T getEntity(Class<T> c, Serializable id);

    <T> T loadProxy(Class<T> c, String id);

    <T> List<T> getAll(Class<T> c);


}
