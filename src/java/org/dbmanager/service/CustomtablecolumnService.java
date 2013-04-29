/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomtablecolumnDao;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author mac
 */
public class CustomtablecolumnService {
    private CustomtablecolumnDao customtablecolumnDao;

    public CustomtablecolumnService() {
        this.customtablecolumnDao=new CustomtablecolumnDao();
    }
    
    public void addCustomtablecolumn(Customtablecolumn customtablecolumn){
        customtablecolumnDao.saveOrUpdate(customtablecolumn);
    }
    
    public void updateCustomtablecolumn(Customtablecolumn customtablecolumn){
        customtablecolumnDao.update(customtablecolumn);
    }
    
    public void deleteCustomtablecolumn(Customtablecolumn customtablecolumn){
        customtablecolumnDao.remove(customtablecolumn);
    }
    
    public void deleteCustomtablecolumnById(Integer idCustomTableColumn){
        customtablecolumnDao.removeById(idCustomTableColumn);
    }
    
    public List<Customtablecolumn> getAllCustomtablecolumn(){
        return customtablecolumnDao.getAll();       
    }
    
    public Customtablecolumn findCustomtablecolumnById(Integer idCustomTableColumn){
        return customtablecolumnDao.getById(idCustomTableColumn);
    }
    
    public List<Customtablecolumn> findCustomtablecolumnByName(String name){
        return customtablecolumnDao.findByProperty("name", name);
    }
    
    public List<Customtablecolumn> findCustomtablecolumnByShowName(String showName){
        return customtablecolumnDao.findByProperty("showName", showName);
    }
    
    public List<Customtablecolumn> findCustomtablecolumnByCustomtable(Customtable customtable){
        return customtablecolumnDao.findByProperty("customtable", customtable);
    }
    
    public List<Customtablecolumn> findcusCustomtablecolumnByPropertys(String[] propertyNames, Object[] values){
        return customtablecolumnDao.findByPropertys(Customtablecolumn.class, propertyNames, values);
    }
    
    public Page pagedQueryCustomtablecolumn(int pageNo, int pageSize, Criterion... criterions){
        return customtablecolumnDao.pagedQuery(Customtablecolumn.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the customtablecolumnDao
     */
    public CustomtablecolumnDao getCustomtablecolumnDao() {
        return customtablecolumnDao;
    }

    /**
     * @param customtablecolumnDao the customtablecolumnDao to set
     */
    public void setCustomtablecolumnDao(CustomtablecolumnDao customtablecolumnDao) {
        this.customtablecolumnDao = customtablecolumnDao;
    }
}
