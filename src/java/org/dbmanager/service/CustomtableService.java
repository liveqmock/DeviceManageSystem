/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomtableDao;
import org.dbmanager.model.Customtable;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author mac
 */
public class CustomtableService {
    private CustomtableDao customtableDao;

    public CustomtableService() {
        this.customtableDao=new CustomtableDao();
    }
    
    public void addCustomtable(Customtable customtable){
        customtableDao.saveOrUpdate(customtable);
    }
    
    public void updateCustomtable(Customtable customtable){
        customtableDao.update(customtable);
    }
    
    public void deleteCustomtable(Customtable customtable){
        customtableDao.remove(customtable);
    }
    
    public void deleteCustomtableById(Integer idCustomTable){
        customtableDao.removeById(idCustomTable);
    }
    
    public List<Customtable> getAllCustomtable(){
        return customtableDao.getAll();
    }
    
    public Customtable findCustomtableById(Integer idCustomTable){
        return customtableDao.getById(idCustomTable);
    }
    
    public List<Customtable> findCustomtableByName(String name){
        return customtableDao.findByProperty("name", name);
    }

    public List<Customtable> findCustomtableByShowName(String showName){
        return customtableDao.findByProperty("showName", showName);
    }
    
    public List<Customtable> findCustomtableByType(int type){
        return customtableDao.findByProperty("type", type);
    }
    
    public List<Customtable> findCustomtableByPropertys(String[] propertyNames, Object[] values){
        return customtableDao.findByPropertys(Customtable.class, propertyNames, values);
    }
    
    public Page pagedQueryCustomtable(int pageNo, int pageSize, Criterion... criterions){
        return customtableDao.pagedQuery(Customtable.class, pageNo, pageSize, criterions);
    }
    /**
     * @return the customtableDao
     */
    public CustomtableDao getCustomtableDao() {
        return customtableDao;
    }

    /**
     * @param customtableDao the customtableDao to set
     */
    public void setCustomtableDao(CustomtableDao customtableDao) {
        this.customtableDao = customtableDao;
    }
}
