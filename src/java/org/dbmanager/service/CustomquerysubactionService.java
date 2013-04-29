/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomquerysubactionDao;
import org.dbmanager.model.Customqueryaction;
import org.dbmanager.model.Customquerysubaction;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomquerysubactionService {
    private CustomquerysubactionDao customquerysubactionDao;

    public CustomquerysubactionService() {
        this.customquerysubactionDao=new CustomquerysubactionDao();
    }
    
    public void addCustomquerysubaction(Customquerysubaction customquerysubaction){
        this.customquerysubactionDao.saveOrUpdate(customquerysubaction);
    }
    
    public void updateCustomquerysubaction(Customquerysubaction customquerysubaction){
        this.customquerysubactionDao.update(customquerysubaction);
    }
    
    public void deleteCustomquerysubaction(Customquerysubaction customquerysubaction){
        this.customquerysubactionDao.remove(customquerysubaction);
    }
    
    public void deleteCustomquerysubactionById(Integer idCustomQuerySubAction){
        this.customquerysubactionDao.removeById(idCustomQuerySubAction);
    }
    
    public List<Customquerysubaction> getAllCustomquerysubaction(){
        return this.customquerysubactionDao.getAll();
    }
    
    public Customquerysubaction findCustomquerysubactionById(Integer idCustomQuerySubAction){
        return this.customquerysubactionDao.getById(idCustomQuerySubAction);    
    }
    
    public List<Customquerysubaction> findCustomquerysubactionByCustomqueryaction(Customqueryaction customqueryaction){
        return this.customquerysubactionDao.findByProperty("customqueryaction", customqueryaction);
    }
    
    public Page pagedQueryCustomquerysubaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customquerysubactionDao.pagedQuery(Customquerysubaction.class, pageNo, pageSize, criterions);
    }
}
