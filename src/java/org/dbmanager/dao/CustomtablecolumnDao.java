/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.dbmanager.model.Customtablecolumn;
import org.hibernate.Session;

/**
 *
 * @author mac
 */
public class CustomtablecolumnDao extends HibernateGenericDao<Customtablecolumn> {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
