package com.pandorabox.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * GenericDataAccesser表示提供基础CRUD功能的数据访问接口 泛型参数T表示实体，ID表示操作时实体的Identifier
 * 
 * @author hywang
 */
public interface GenericDataAccessor<T extends Serializable, ID extends Serializable> {

    public SessionFactory getSessionFactory();

    public void setSessionFactory(SessionFactory sessionFactory);

    /**
     * 加载所有PO实例
     * 
     * @return 返回泛型T对应Class的所有实例
     */
    public List<T> loadAll();

    /**
     * 根据ID加载PO实例
     * 
     * @param id
     * @return 返回相应的持久化PO实例
     */
    public T load(ID id);

    /**
     * 根据ID获取PO实例
     * 
     * @param id
     * @return 返回相应的持久化PO实例
     */
    public T get(ID id);

    /**
     * 保存PO
     * 
     * @param entity
     */
    public ID save(T entity);

    /**
     * 删除PO
     * 
     * @param entity
     */
    public void remove(T entity);

    /**
     * 更改PO
     * 
     * @param entity
     */
    public void update(T entity);

    /**
     * 执行带参的HQL查询
     * 
     * @param sql
     * @param params
     * @return 查询结果
     */
    public List find(String hql, Object... params);

    /**
     * 对延迟加载的实体PO执行初始化
     * 
     * @param entity
     */
    public void initialize(Object entity);

    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置. 留意可以连续设置,如下：
     * 
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 
     * 调用方式如下：
     * 
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     * 
     * @param values 可变参数.
     */
    public Query createQuery(String hql, Object... values);

    public Session getSession();
}
