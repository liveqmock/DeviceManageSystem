/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomactionDao;
import org.dbmanager.model.Customaction;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomactionService {
    private CustomactionDao customactionDao;

    public CustomactionService() {
        this.customactionDao=new CustomactionDao();
    }
    
    public void addCustomaction(Customaction customaction){
        this.customactionDao.saveOrUpdate(customaction);
    }
    
    public void updateCustomaction(Customaction customaction){
        this.customactionDao.update(customaction);
    }
    
    public void deleteCustomaction(Customaction customaction){
        this.customactionDao.remove(customaction);
    }
    
    public void deleteCustomactionById(Integer idCustomAction){
        this.customactionDao.removeById(idCustomAction);
    }
    
    public List<Customaction> getAllCustomaction(){
        return this.customactionDao.getAll();
    }
    
    public Customaction findCustomactionById(Integer idCustomAction){
        return this.customactionDao.getById(idCustomAction);    
    }
    
    public List<Customaction> findCustomactionByName(String name){
        return this.customactionDao.findByProperty("name", name);
    }
    
    public Page pagedQueryCustomaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customactionDao.pagedQuery(Customaction.class, pageNo, pageSize, criterions);
    }
    
    /**
     * @return the customactionDao
     */
    public CustomactionDao getCustomactionDao() {
        return customactionDao;
    }

    /**
     * @param customactionDao the customactionDao to set
     */
    public void setCustomactionDao(CustomactionDao customactionDao) {
        this.customactionDao = customactionDao;
    }
    
}
