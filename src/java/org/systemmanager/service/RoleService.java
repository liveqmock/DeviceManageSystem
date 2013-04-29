/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.hibernate.criterion.Criterion;
import org.systemmanager.dao.RoleDao;
import org.systemmanager.model.Role;

/**
 *
 * @author Administrator
 */
public class RoleService {
    private RoleDao roleDao;

    public RoleService() {
        this.roleDao=new RoleDao();
    }
    
    public void addRole(Role role){
        getRoleDao().saveOrUpdate(role);
    }
    
    public void updateRole(Role role){
        getRoleDao().update(role);
    }
    
    public void deleteRole(Role role){
        getRoleDao().remove(role);
    }
    
    public void deleteRoleById(Integer idRole){
        getRoleDao().removeById(idRole);
    }
    
    public List<Role> getAllRole(){
        return getRoleDao().getAll();
    }
    
    public Role findRoleById(Integer idRole){
        return getRoleDao().getById(idRole);
    }
    
    public List<Role> findRoleByName(String name){
        return getRoleDao().findByProperty("name", name);
    }
    
    public Page pagedQueryRole(int pageNo, int pageSize, Criterion... criterions){
        return getRoleDao().pagedQuery(Role.class, pageNo, pageSize, criterions);
    } 

    /**
     * @return the roleDao
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    /**
     * @param roleDao the roleDao to set
     */
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
