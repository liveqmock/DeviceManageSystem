package org.dao.util;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mac
 */
@SuppressWarnings("unchecked")
public abstract class HibernateGenericDao<T> extends HibernateDaoSupport<T> implements EntityDao<T> {

    protected Class<T> entityClass;

    public HibernateGenericDao() {
        /**
         * this.getClass()的目的是返回当前对象运行时的类 通过工具类GenericUtils返回泛型T的实际类对象
         */
        entityClass = GenericUtils.getSuperClassGenericType(getClass());
    }

    @Override
    public T getById(Serializable id) {
        return getById(entityClass, id);
    }

    @Override
    public List<T> getAll() {
        return getAll(entityClass);
    }

    @Override
    public String getIdName(Class clazz) {
        return null;
    }

    @Override
    public void removeById(Serializable id) {
        removeById(entityClass, id);
    }

    @Override
    public void save(T newInstance) {
        saveOrUpdate(newInstance);
    }

    /**
     * 查询全部，带排序
     *
     * @param orderBy
     * @param isAsc
     * @return
     */
    public List<T> getAll(String orderBy, boolean isAsc) {
        return getAll(entityClass, orderBy, isAsc);
    }

    /**
     * 根据属性名和属性值查询对象
     *
     * @param propertyName
     * @param value
     * @return
     */
    public List<T> findByProperty(String propertyName, Object value) {
        return findByProperty(entityClass, propertyName, value);
    }

    /**
     * 根据属性名和属性值进行查询对象，带排序
     *
     * @param propertyName
     * @param value
     * @param isAsc
     * @param orderBy
     * @return
     */
    public List<T> findByProperty(String propertyName, Object value, boolean isAsc, String orderBy) {
        return findByProperty(entityClass, propertyName, value, orderBy, isAsc);
    }

    /**
     * 根据属性名和属性值进行查询对象，返回符合条件的唯一对象。 如果对象不唯一将抛出异常
     *
     * @param <T>
     * @param propertyName
     * @param value
     * @return
     */
    public <T> T findUniqueByProperty(String propertyName, Object value) {
        return (T) findUniqueByProperty(entityClass, propertyName, value);
    }
}
