/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.dao;

import org.dao.util.HibernateGenericDao;
import org.dao.util.HibernateSessionFactory;
import org.dbmanager.model.Commontablecolumn;
import org.hibernate.Session;

/**
 *
 * @author Administrator
 */
public class CommontablecolumnDao extends HibernateGenericDao<Commontablecolumn> {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }
}
