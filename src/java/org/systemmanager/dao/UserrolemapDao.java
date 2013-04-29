/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.systemmanager.model.Userrolemap;

/**
 *
 * @author mac
 */
public class UserrolemapDao extends HibernateGenericDao<Userrolemap> {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
