/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.hibernate.criterion.Criterion;
import org.systemmanager.dao.ActionmodulegroupDao;
import org.systemmanager.model.Actionmodulegroup;

/**
 *
 * @author Administrator
 */
public class ActionmodulegroupService {
    private ActionmodulegroupDao actionmodulegroupDao;
    
    public ActionmodulegroupService(){
        this.actionmodulegroupDao=new ActionmodulegroupDao();
    }
    
    public void addActionmodulegroup(Actionmodulegroup actionmodulegroup){
        this.actionmodulegroupDao.saveOrUpdate(actionmodulegroup);
    }
    
    public void updateActionmodulegroup(Actionmodulegroup actionmodulegroup){
        this.actionmodulegroupDao.update(actionmodulegroup);
    }
    
    public void deleteActionmodulegroup(Actionmodulegroup actionmodulegroup){
        this.actionmodulegroupDao.remove(actionmodulegroup);
    }
    
    public void deleteActionmodulegroupById(Integer idActionModuleGroup){
        this.actionmodulegroupDao.removeById(idActionModuleGroup);
    }
    
    public List<Actionmodulegroup> getAllActionmodulegroup(){
        return this.actionmodulegroupDao.getAll();
    }
    
    public Actionmodulegroup findActionmodulegroupById(Integer idActionModuleGroup){
        return this.actionmodulegroupDao.getById(idActionModuleGroup);
    }
    
    public List<Actionmodulegroup> findActionmodulegroupByName(String name){
        return this.actionmodulegroupDao.findByProperty("name", name);
    }
    
    public List<Actionmodulegroup> findActionmodulegroupByPropertys(String[] propertyNames,Object[] values){
        return this.actionmodulegroupDao.findByPropertys(Actionmodulegroup.class, propertyNames, values);
    }
    
    public Page pageQueryActionmodulegroup(int pageNo, int pageSize, Criterion... criterions){
        return this.actionmodulegroupDao.pagedQuery(Actionmodulegroup.class, pageNo, pageSize, criterions);
    }
    

    /**
     * @return the actionmodulegroupDao
     */
    public ActionmodulegroupDao getActionmodulegroupDao() {
        return actionmodulegroupDao;
    }

    /**
     * @param actionmodulegroupDao the actionmodulegroupDao to set
     */
    public void setActionmodulegroupDao(ActionmodulegroupDao actionmodulegroupDao) {
        this.actionmodulegroupDao = actionmodulegroupDao;
    }
    
}
