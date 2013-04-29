/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomtablecustomactionmapDao;
import org.dbmanager.model.Customtablecustomactionmap;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomtablecustomactionmapService {
    
    private CustomtablecustomactionmapDao customtablecustomactionmapDao;

    public CustomtablecustomactionmapService() {
        this.customtablecustomactionmapDao=new CustomtablecustomactionmapDao();
    }
    
    public void addCustomtablecustomactionmap(Customtablecustomactionmap customtablecustomactionmap){
        this.customtablecustomactionmapDao.saveOrUpdate(customtablecustomactionmap);
    }
    
    public void updateCustomtablecustomactionmap(Customtablecustomactionmap customtablecustomactionmap){
        this.customtablecustomactionmapDao.update(customtablecustomactionmap);
    }
    
    public void deleteCustomtablecustomactionmap(Customtablecustomactionmap customtablecustomactionmap){
        this.customtablecustomactionmapDao.remove(customtablecustomactionmap);
    }
    
    public void deleteCustomtablecustomactionmapById(Integer idCustomTableCustomActionMap){
        this.customtablecustomactionmapDao.removeById(idCustomTableCustomActionMap);
    }
    
    public List<Customtablecustomactionmap> getAllCustomtablecustomactionmap(){
        return this.customtablecustomactionmapDao.getAll();
    }
    
    public Customtablecustomactionmap findCustomtablecustomactionmapById(Integer idCustomTableCustomActionMap){
        return this.customtablecustomactionmapDao.getById(idCustomTableCustomActionMap);    
    }
    
    public List<Customtablecustomactionmap> findCustomtablecustomactionmapByCustomtable(int idCustomTable){
        return this.customtablecustomactionmapDao.findByProperty("idCustomTable", idCustomTable);
    }
    
    public List<Customtablecustomactionmap> findCustomtablecustomactionmapByCustomaction(int idCustomAction){
        return this.customtablecustomactionmapDao.findByProperty("idCustomAction", idCustomAction);
    }
    
    public Page pagedQueryCustomcustomaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customtablecustomactionmapDao.pagedQuery(Customtablecustomactionmap.class, pageNo, pageSize, criterions);
    }
    
    /**
     * @return the customtablecustomactionmapDao
     */
    public CustomtablecustomactionmapDao getCustomtablecustomactionmapDao() {
        return customtablecustomactionmapDao;
    }

    /**
     * @param customtablecustomactionmapDao the customtablecustomactionmapDao to set
     */
    public void setCustomtablecustomactionmapDao(CustomtablecustomactionmapDao customtablecustomactionmapDao) {
        this.customtablecustomactionmapDao = customtablecustomactionmapDao;
    }
    
}
