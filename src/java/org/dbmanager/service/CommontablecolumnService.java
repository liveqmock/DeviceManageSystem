/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CommontablecolumnDao;
import org.dbmanager.model.Commontablecolumn;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CommontablecolumnService {
    private CommontablecolumnDao commontablecolumnDao;
    
    public CommontablecolumnService(){
        this.commontablecolumnDao=new CommontablecolumnDao();
    }
    
    public void addCommontablecolumn(Commontablecolumn commontablecolumn){
        getCommontablecolumnDao().saveOrUpdate(commontablecolumn);
    }
    
    public void updateCommontablecolumn(Commontablecolumn commontablecolumn){
        getCommontablecolumnDao().update(commontablecolumn);
    }
    
    public void deleteCommontablecolumn(Commontablecolumn commontablecolumn){
        getCommontablecolumnDao().remove(commontablecolumn);
    }
    
    public void deleteCommontablecolumnById(Integer idCommonTableColumn){
        getCommontablecolumnDao().removeById(idCommonTableColumn);
    }
    
    public List<Commontablecolumn> getAllCommontablecolumn(){
        return getCommontablecolumnDao().getAll();
    }
    
    public Commontablecolumn findCommontablecolumnById(Integer idCommonTableColumn){
        return getCommontablecolumnDao().getById(idCommonTableColumn);
    }
    
    public List<Commontablecolumn> findCommontablecolumnByName(String name){
        return getCommontablecolumnDao().findByProperty("name", name);
    }
    
    public List<Commontablecolumn> findCommontablecolumnByShowName(String showName){
        return getCommontablecolumnDao().findByProperty("showName", showName);
    }
    
    public List<Commontablecolumn> findCommontablecolumnByTableType(int tableType){
        return getCommontablecolumnDao().findByProperty("tableType", tableType);
    }
    
    public List<Commontablecolumn> findCommontablecolumnByPropertys(String[] propertyNames,Object[] values){
        return getCommontablecolumnDao().findByPropertys(Commontablecolumn.class, propertyNames, values);
    }
    
    public Page pageQueryCommontablecolumn(int pageNo, int pageSize, Criterion... criterions){
        return getCommontablecolumnDao().pagedQuery(Commontablecolumn.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the commontablecolumnDao
     */
    public CommontablecolumnDao getCommontablecolumnDao() {
        return commontablecolumnDao;
    }

    /**
     * @param commontablecolumnDao the commontablecolumnDao to set
     */
    public void setCommontablecolumnDao(CommontablecolumnDao commontablecolumnDao) {
        this.commontablecolumnDao = commontablecolumnDao;
    }
}

