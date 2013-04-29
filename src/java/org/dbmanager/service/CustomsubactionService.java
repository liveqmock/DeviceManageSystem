/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomsubactionDao;
import org.dbmanager.model.Customaction;
import org.dbmanager.model.Customsubaction;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomsubactionService {
    
    private CustomsubactionDao customsubactionDao;

    public CustomsubactionService() {
        this.customsubactionDao=new CustomsubactionDao();
    }
    
    public void addCustomsubaction(Customsubaction customsubaction){
        this.customsubactionDao.saveOrUpdate(customsubaction);
    }
    
    public void updateCustomsubaction(Customsubaction customsubaction){
        this.customsubactionDao.update(customsubaction);
    }
    
    public void deleteCustomsubaction(Customsubaction customsubaction){
        this.customsubactionDao.remove(customsubaction);
    }
    
    public void deleteCustomsubactionById(Integer idCustomSubAction){
        this.customsubactionDao.removeById(idCustomSubAction);
    }
    
    public List<Customsubaction> getAllCustomsubaction(){
        return this.customsubactionDao.getAll();
    }
    
    public Customsubaction findCustomsubactionById(Integer idCustomSubAction){
        return this.customsubactionDao.getById(idCustomSubAction);    
    }
    
    public List<Customsubaction> findCustomsubactionByCustomaction(Customaction customaction){
        return this.customsubactionDao.findByProperty("customaction", customaction);
    }
    
    public Page pagedQueryCustomsubaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customsubactionDao.pagedQuery(Customsubaction.class, pageNo, pageSize, criterions);
    }
    
    /**
     * @return the customsubactionDao
     */
    public CustomsubactionDao getCustomsubactionDao() {
        return customsubactionDao;
    }

    /**
     * @param customsubactionDao the customsubactionDao to set
     */
    public void setCustomsubactionDao(CustomsubactionDao customsubactionDao) {
        this.customsubactionDao = customsubactionDao;
    }
}
