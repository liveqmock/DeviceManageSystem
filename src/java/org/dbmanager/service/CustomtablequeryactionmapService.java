/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.dbmanager.dao.CustomtablequeryactionmapDao;
import org.dbmanager.model.Customtablequeryactionmap;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Administrator
 */
public class CustomtablequeryactionmapService {
    private CustomtablequeryactionmapDao customtablequeryactionmapDao;

    public CustomtablequeryactionmapService() {
        this.customtablequeryactionmapDao=new CustomtablequeryactionmapDao();
    }
    
    public void addCustomtablequeryactionmap(Customtablequeryactionmap customtablequeryactionmap){
        this.customtablequeryactionmapDao.saveOrUpdate(customtablequeryactionmap);
    }
    
    public void updateCustomtablequeryactionmap(Customtablequeryactionmap customtablequeryactionmap){
        this.customtablequeryactionmapDao.update(customtablequeryactionmap);
    }
    
    public void deleteCustomtablequeryactionmap(Customtablequeryactionmap customtablequeryactionmap){
        this.customtablequeryactionmapDao.remove(customtablequeryactionmap);
    }
    
    public void deleteCustomtablequeryactionmapById(Integer idCustomTableQueryActionMap){
        this.customtablequeryactionmapDao.removeById(idCustomTableQueryActionMap);
    }
    
    public List<Customtablequeryactionmap> getAllCustomtablequeryactionmap(){
        return this.customtablequeryactionmapDao.getAll();
    }
    
    public Customtablequeryactionmap findCustomtablequeryactionmapById(Integer idCustomTableQueryActionMap){
        return this.customtablequeryactionmapDao.getById(idCustomTableQueryActionMap);    
    }
    
    public List<Customtablequeryactionmap> findCustomtablequeryactionmapByCustomtable(int idCustomTable){
        return this.customtablequeryactionmapDao.findByProperty("idCustomTable", idCustomTable);
    }
    
    public List<Customtablequeryactionmap> findCustomtablequeryactionmapByQueryaction(int idCustomQueryAction){
        return this.customtablequeryactionmapDao.findByProperty("idCustomQueryAction", idCustomQueryAction);
    }
    
    public Page pagedQueryCustomqueryaction(int pageNo, int pageSize, Criterion... criterions){
        return this.customtablequeryactionmapDao.pagedQuery(Customtablequeryactionmap.class, pageNo, pageSize, criterions);
    }

    /**
     * @return the customtablequeryactionmapDao
     */
    public CustomtablequeryactionmapDao getCustomtablequeryactionmapDao() {
        return customtablequeryactionmapDao;
    }

    /**
     * @param customtablequeryactionmapDao the customtablequeryactionmapDao to set
     */
    public void setCustomtablequeryactionmapDao(CustomtablequeryactionmapDao customtablequeryactionmapDao) {
        this.customtablequeryactionmapDao = customtablequeryactionmapDao;
    }

}
