/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.systemmanager.dao.RoleactionmodulemapDao;
import org.systemmanager.model.Actionmodule;
import org.systemmanager.model.Role;
import org.systemmanager.model.Roleactionmodulemap;

/**
 *
 * @author Administrator
 */
public class RoleactionmodulemapService {
    private RoleactionmodulemapDao roleactionmodulemapDao;
    
    public RoleactionmodulemapService(){
        this.roleactionmodulemapDao=new RoleactionmodulemapDao();
    }
    
    public void addRoleactionmodulemap(Roleactionmodulemap roleactionmodulemap){
        roleactionmodulemapDao.saveOrUpdate(roleactionmodulemap);
    }
    
    public void updateRoleactionmodulemap(Roleactionmodulemap roleactionmodulemap){
        roleactionmodulemapDao.update(roleactionmodulemap);
    }
    
    public void deleteRoleactionmodulemap(Roleactionmodulemap roleactionmodulemap){
        roleactionmodulemapDao.remove(roleactionmodulemap);
    }
    
    public void deleteRoleactionmodulemapById(Integer idRoleActionModuleMap){
        roleactionmodulemapDao.removeById(idRoleActionModuleMap);
    }
    
    public List<Roleactionmodulemap> getAllRoleactionmodulemap(){
        return roleactionmodulemapDao.getAll();
    }
    
    public Roleactionmodulemap findRoleactionmodulemapById(Integer idRoleActionModuleMap){
        return roleactionmodulemapDao.getById(idRoleActionModuleMap);
    }
    
    public List<Roleactionmodulemap> findRoleactionmodulemapByRole(Role role){
        return roleactionmodulemapDao.findByProperty("role", role);
    }
    
    public List<Roleactionmodulemap> findRoleactionmodulemapByActionmodule(Actionmodule actionmodule){
        return roleactionmodulemapDao.findByProperty("actionmodule", actionmodule);
    }
    
    public List<Roleactionmodulemap> findRoleactionmodulemapByPropertys(String[] propertyNames, Object[] values){
        return roleactionmodulemapDao.findByPropertys(Roleactionmodulemap.class, propertyNames, values);
    }

    /**
     * @return the roleactionmodulemapDao
     */
    public RoleactionmodulemapDao getRoleactionmodulemapDao() {
        return roleactionmodulemapDao;
    }

    /**
     * @param roleactionmodulemapDao the roleactionmodulemapDao to set
     */
    public void setRoleactionmodulemapDao(RoleactionmodulemapDao roleactionmodulemapDao) {
        this.roleactionmodulemapDao = roleactionmodulemapDao;
    }

    
}
