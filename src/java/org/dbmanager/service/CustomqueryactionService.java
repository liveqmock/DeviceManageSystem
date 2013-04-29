/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomqueryactionDao;
import org.dbmanager.model.Customqueryaction;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomqueryactionService {
    private CustomqueryactionDao customqueryactionDao;

    public CustomqueryactionService() {
        this.customqueryactionDao=new CustomqueryactionDao();
    }
    
    public void addCustomqueryaction(Customqueryaction customqueryaction){
        this.customqueryactionDao.saveOrUpdate(customqueryaction);
    }
    
    public void updateCustomqueryaction(Customqueryaction customqueryaction){
        this.customqueryactionDao.update(customqueryaction);
    }
    
    public void deleteCustomqueryaction(Customqueryaction customqueryaction){
        this.customqueryactionDao.remove(customqueryaction);
    }
    
    public void deleteCustomqueryactionById(Integer idCustomQueryAction){
        this.customqueryactionDao.removeById(idCustomQueryAction);
    }
    
    public List<Customqueryaction> getAllCustomqueryaction(){
        return this.customqueryactionDao.getAll();
    }
    
    public Customqueryaction findCustomqueryactionById(Integer idCustomQueryAction){
        return this.customqueryactionDao.getById(idCustomQueryAction);    
    }
    
    public List<Customqueryaction> findCustomqueryactionByName(String name){
        return this.customqueryactionDao.findByProperty("name", name);
    }
    
    public Page pagedQueryCustomqueryaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customqueryactionDao.pagedQuery(Customqueryaction.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the customqueryactionDao
     */
    public CustomqueryactionDao getCustomqueryactionDao() {
        return customqueryactionDao;
    }

    /**
     * @param customqueryactionDao the customqueryactionDao to set
     */
    public void setCustomqueryactionDao(CustomqueryactionDao customqueryactionDao) {
        this.customqueryactionDao = customqueryactionDao;
    }
}
