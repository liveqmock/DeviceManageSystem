/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.systemmanager.model.Role;

/**
 *
 * @author mac
 */
public class RoleDao extends HibernateGenericDao<Role> {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
