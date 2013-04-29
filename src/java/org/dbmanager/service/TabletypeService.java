/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.TabletypeDao;
import org.dbmanager.model.Tabletype;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class TabletypeService {
    private TabletypeDao tabletypeDao;

    public TabletypeService() {
        this.tabletypeDao=new TabletypeDao();
    }
    
    public void addTabletype(Tabletype tabletype){
        getTabletypeDao().saveOrUpdate(tabletype);
    }
    
    public void updateTabletype(Tabletype tabletype){
        getTabletypeDao().update(tabletype);
    }
    
    public void deleteTabletype(Tabletype tabletype){
        getTabletypeDao().remove(tabletype);
    }
    
    public void deleteCustomtableById(Integer idTableType){
        getTabletypeDao().removeById(idTableType);
    }
    
    public List<Tabletype> getAllTabletype(){
        return getTabletypeDao().getAll();
    }
    
    public Tabletype findTabletypeById(Integer idTableType){
        return getTabletypeDao().getById(idTableType);
    }
    
    public List<Tabletype> findTabletypeByTableTypeName(String tableTypeName){
        return getTabletypeDao().findByProperty("tableTypeName", tableTypeName);
    }

    public List<Tabletype> findTabletypeByTabletype(int tableType){
        return getTabletypeDao().findByProperty("tableType", tableType);
    }
    
    public List<Tabletype> findTabletypeByPropertys(String[] propertyNames, Object[] values){
        return getTabletypeDao().findByPropertys(Tabletype.class, propertyNames, values);
    }
    
    public Page pagedQueryTabletype(int pageNo, int pageSize, Criterion... criterions){
        return getTabletypeDao().pagedQuery(Tabletype.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the tabletypeDao
     */
    public TabletypeDao getTabletypeDao() {
        return tabletypeDao;
    }

    /**
     * @param tabletypeDao the tabletypeDao to set
     */
    public void setTabletypeDao(TabletypeDao tabletypeDao) {
        this.tabletypeDao = tabletypeDao;
    }
}
