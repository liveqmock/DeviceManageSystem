/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.dbmanager.model.Customsubaction;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public class CustomsubactionDao extends HibernateGenericDao<Customsubaction>{
    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
