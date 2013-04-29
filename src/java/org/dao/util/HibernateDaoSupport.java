package org.dao.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.OrderEntry;

/**
 * Dao的基类.
 *
 * 提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 *
 * @author mac
 */
public abstract class HibernateDaoSupport<T> implements HibernateDaoHandler {

    private static final Log log = LogFactory.getLog(HibernateDaoSupport.class);

    public Object execute(HibernateCallback action) {
        Assert.notNull(action, "Callback object 对象不能为 Null ");
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        Transaction tr = session.beginTransaction();
        Object result = action.doInHibernate(session);
        tr.commit();
        return result;
    }

    /**
     * 获取全部的对象
     *
     * @param <T>
     * @param entityClass 对象类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(final Class<T> entityClass) {
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                Criteria criteria = session.createCriteria(entityClass);
                List<T> criteriaList=criteria.list();
                return criteriaList;
            }
        });
    }

    /**
     * 获取全部对象，带排序功能
     *
     * @param <T>
     * @param entityClass 实体对象
     * @param orderBy　排序字段
     * @param isAsc 升序或降序
     * @return
     */
    @SuppressWarnings({"hiding", "hiding", "unchecked"})
    public <T> List<T> getAll(final Class<T> entityClass, final String orderBy, final boolean isAsc) {
        Assert.hasText(orderBy);
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                if (isAsc) {
                    Criteria criteria = session.createCriteria(entityClass).addOrder(Order.asc(orderBy));
                    List<T> criteriaList = criteria.list();
                    return criteriaList;
                } else {
                    Criteria criteria = session.createCriteria(entityClass).addOrder(Order.desc(orderBy));
                    List<T> criteriaList = criteria.list();
                    return criteriaList;
                }
            }
        });

    }

    /**
     * 保存对象
     *
     * @param newInstance
     */
    @SuppressWarnings("unused")
    public void saveOrUpdate(final T newInstance) {
        execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                session.saveOrUpdate(newInstance);
                return null;
            }
        });
    }

    /**
     * 更新对象
     *
     * @param transientObject
     */
    public void update(final T transientObject) {
        execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                session.update(transientObject);
                return null;
            }
        });
    }

    /**
     * 删除对象
     *
     * @param transientObject
     */
    public void remove(final T transientObject) {
        execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session.delete(transientObject);
                return null;
            }
        });
    }

    /**
     *
     * @param entityClass
     * @param id
     */
    public void removeById(Class<T> entityClass, Serializable id) {
                remove(getById(entityClass, id));
            }

    /**
     * 根据Id获取对象。
     *
     * @param <T>
     * @param entityClass
     * @param id 实体Id
     * @return 实体对象
     */
    @SuppressWarnings({"hiding", "unchecked"})
    public <T> T getById(final Class<T> entityClass, final Serializable id) {
        return (T) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                Object object = session.get(entityClass, id);
                return object;
            }
        });
    }

    /**
     * 创建一个Query对象。
     *
     * @param hql
     * @param values
     * @return
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    /**
     * 创建Criteria对象。
     *
     * @param <T>
     * @param entityClass
     * @param criterions
     * @return
     */
    public <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
        Criteria criteria = HibernateSessionFactory.getSessionFactory().getCurrentSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 创建Criteria对象，有排序功能。
     *
     * @param <T>
     * @param entityClass
     * @param orderBy
     * @param isAsc
     * @param criterions
     * @return
     */
    public <T> Criteria createCriteria(Class<T> entityClass, String orderBy, boolean isAsc, Criterion... criterions) {
        Assert.hasText(orderBy);
        Criteria criteria = createCriteria(entityClass, criterions);
        if (isAsc) {
            criteria.addOrder(Order.asc(orderBy));
        } else {
            criteria.addOrder(Order.desc(orderBy));
        }
        return criteria;
    }

    /**
     * 根据hql查询
     *
     * @param hql
     * @param values
     * @return
     */
    public <T> List<T> findByHql(final String hql, final Object... values) {
        Assert.hasText(hql);
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                Query query = createQuery(hql, values);
                List<T> queryList = query.list();
                return queryList;
            }
        });
    }

    /**
     * 根据属性名和属性值查询.
     *
     * @return
     */
    public <T> List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                Criteria criteria = createCriteria(entityClass, Restrictions.eq(propertyName, value));
                List<T> criteriaList = criteria.list();
                return criteriaList;
            }
        });
    }

    /**
     * 根据属性名和属性值查询. 有排序
     *
     * @param <T>
     * @param entityClass
     * @param propertyName
     * @param value
     * @param orderBy
     * @param isAsc
     * @return
     */
    public <T> List<T> findByProperty(final Class<T> entityClass, final String propertyName, final Object value, final String orderBy, final boolean isAsc) {
        Assert.hasText(propertyName);
        Assert.hasText(orderBy);
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                Criteria criteria = createCriteria(entityClass, orderBy, isAsc, Restrictions.eq(propertyName, value));
                List<T> criteriaList = criteria.list();
                return criteriaList;
            }
        });

    }

    /**
     * 根据多个属性名和多个属性值查询.
     *
     * @param <T>
     * @param entityClass
     * @param propertyNames
     * @param values
     * @return
     */
    public <T> List<T> findByPropertys(final Class<T> entityClass, final String[] propertyNames, final Object[] values) {
        return (List<T>) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                StringBuffer strBuffer = new StringBuffer();
                strBuffer.append("from " + entityClass.getName());
                strBuffer.append(" as model where ");
                for (int i = 0; i < propertyNames.length; i++) {
                    if (i != 0) {
                        strBuffer.append(" and");
                    }
                    strBuffer.append(" model.");
                    strBuffer.append(propertyNames[i]);
                    strBuffer.append("=");
                    strBuffer.append("? ");
                }
                String queryString = strBuffer.toString();
                Query query = createQuery(queryString, values);
                List<T> queryList = query.list();
                return queryList;
            }
        });
    }

    /**
     * 根据属性名和属性值 查询 且要求对象唯一.
     *
     * @return 符合条件的唯一对象.
     */
    public <T> T findUniqueByProperty(final Class<T> entityClass, final String propertyName, final Object value) {
        Assert.hasText(propertyName);
        return (T) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                return (T) createCriteria(entityClass, Restrictions.eq(propertyName, value)).uniqueResult();
            }
        });

    }

    /**
     * 分页 通过hql进行
     *
     * @param hql
     * @param pageNo
     * @param pageSize
     * @param values
     * @return
     */
    public Page pagedQuery(final String hql, final int pageNo, final int pageSize, final Object... values) {
        Assert.hasText(hql);
        Assert.isTrue(pageNo >= 1);
        return (Page) execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
                String countQueryString = "select count(*)" + removeSelect(removeOrders(hql));
                System.out.println(countQueryString);
                List countList = findByHql(countQueryString, values);
                long totalCount = (Long) countList.get(0);
                System.out.println(totalCount);
                if (totalCount < 1) {
                    return new Page();
                }
                int startIndex = Page.getStartOfPage(pageNo, pageSize);
                Query query = createQuery(hql, values);
                List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
                return new Page(startIndex, totalCount, pageSize, list);
            }
        });
    }

    /**
     * 分页 通过criteria
     *
     * @param criteria
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
        Assert.notNull(criteria);
        Assert.isTrue(pageNo >= 1);
        CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

        //先把Projection和OrderBy条件取出来,清空两者来执行Count操作  
        Projection projection = criteriaImpl.getProjection();
        List<CriteriaImpl.OrderEntry> orderEntitys = null;
        try {
            orderEntitys = (List<OrderEntry>) BeanUtils.forceGetProperty(criteriaImpl, "orderEntries");
            BeanUtils.forceSetProperty(criteriaImpl, "orderEntries", new ArrayList());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //取得总的数据数  
        long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        //将之前的Projection和OrderBy条件重新设回去  
        criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }

        try {
            BeanUtils.forceSetProperty(criteriaImpl, "orderEntries", orderEntitys);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (totalCount < 1) {
            return new Page();
        }
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List data = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
        return new Page(startIndex, totalCount, pageSize, data);
    }

    /**
     * 分页查询函数
     *
     * @param entityClass
     * @param pageNo
     * @param pageSize
     * @param criterions
     * @return
     */
    public Page pagedQuery(Class<T> entityClass, int pageNo, int pageSize, Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, criterions);
        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 分页查询带排序
     *
     * @param entityClass
     * @param pageNo
     * @param pageSize
     * @param orderBy
     * @param isAsc
     * @param criterions
     * @return
     */
    public Page pagedQuery(Class<T> entityClass, int pageNo, int pageSize, String orderBy, boolean isAsc, Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, orderBy, isAsc, criterions);
        return pagedQuery(criteria, pageNo, pageSize);
    }

    /**
     * 去除hql的select子句。
     *
     * @param hql
     * @return
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, hql);
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderBy子句。
     *
     * @param hql
     * @return
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
