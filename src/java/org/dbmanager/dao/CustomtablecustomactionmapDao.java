/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.dbmanager.model.Customtablecustomactionmap;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public class CustomtablecustomactionmapDao extends HibernateGenericDao<Customtablecustomactionmap>{
    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
