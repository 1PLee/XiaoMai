package main.dao;

import main.util.ResultMessage;

import java.util.List;

/**
 * Created by liyipeng on 2018/2/13.
 */
public interface BaseDAO {

    int save(Object entity) throws Exception;

    ResultMessage saveOrUpdate(Object entity);

    ResultMessage update(Object entity);

    <T> T getEntity(Class<T> c, int id);

    <T> T loadProxy(Class<T> c, int id);

    <T> List<T> getAll(Class<T> c);


}
