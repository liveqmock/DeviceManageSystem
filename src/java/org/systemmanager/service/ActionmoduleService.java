/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.hibernate.criterion.Criterion;
import org.systemmanager.dao.ActionmoduleDao;
import org.systemmanager.model.Actionmodule;
import org.systemmanager.model.Actionmodulegroup;

/**
 *
 * @author Administrator
 */
public class ActionmoduleService {
    private ActionmoduleDao actionmoduleDao;

    public ActionmoduleService() {
        this.actionmoduleDao=new ActionmoduleDao();
    }
    
    public void addActionmodule(Actionmodule actionmodule){
        this.actionmoduleDao.saveOrUpdate(actionmodule);
    }
    
    public void updateActionmodule(Actionmodule actionmodule){
        this.actionmoduleDao.update(actionmodule);
    }
    
    public void deleteActionmodule(Actionmodule actionmodule){
        this.actionmoduleDao.remove(actionmodule);
    }
    
    public void deleteActionmoduleById(Integer idActionModule){
        this.actionmoduleDao.removeById(idActionModule);
    }
    
    public List<Actionmodule> getAllActionmodule(){
        return this.actionmoduleDao.getAll();
    }
    
    public Actionmodule findActionmoduleById(Integer idActionModule){
        return this.actionmoduleDao.getById(idActionModule);
    }
    
    public List<Actionmodule> findActionmoduleByName(String name){
        return this.actionmoduleDao.findByProperty("name", name);
    }
    
    public List<Actionmodule> findActionmoduleByActionmodulegroup(Actionmodulegroup actionmodulegroup){
        return this.actionmoduleDao.findByProperty("actionmodulegroup", actionmodulegroup);
    }
    
    public List<Actionmodule> findActionmoduleByPropertys(String[] propertyNames,Object[] values){
        return this.actionmoduleDao.findByPropertys(Actionmodule.class, propertyNames, values);
    }
    
    public Page pageQueryActionmodule(int pageNo, int pageSize, Criterion... criterions){
        return this.actionmoduleDao.pagedQuery(Actionmodule.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the actionmoduleDao
     */
    public ActionmoduleDao getActionmoduleDao() {
        return actionmoduleDao;
    }

    /**
     * @param actionmoduleDao the actionmoduleDao to set
     */
    public void setActionmoduleDao(ActionmoduleDao actionmoduleDao) {
        this.actionmoduleDao = actionmoduleDao;
    }
    
}
