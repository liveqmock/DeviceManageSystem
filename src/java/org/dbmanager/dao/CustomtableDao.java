/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.dbmanager.model.Customtable;
import org.hibernate.Session;

/**
 *
 * @author mac
 */
public class CustomtableDao extends HibernateGenericDao<Customtable> {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
