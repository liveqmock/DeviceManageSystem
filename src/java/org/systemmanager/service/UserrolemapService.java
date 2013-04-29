/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.systemmanager.dao.UserrolemapDao;
import org.systemmanager.model.Role;
import org.systemmanager.model.User;
import org.systemmanager.model.Userrolemap;

/**
 *
 * @author Administrator
 */
public class UserrolemapService {
    private UserrolemapDao userrolemapDao;
    
    public UserrolemapService(){
        this.userrolemapDao=new UserrolemapDao();
    }
    
    public void addUserrolemap(Userrolemap userrolemap){
        userrolemapDao.saveOrUpdate(userrolemap);
    }
    
    public void updateUserrolemap(Userrolemap userrolemap){
        userrolemapDao.update(userrolemap);
    }
    
    public void deleteUserrolemap(Userrolemap userrolemap){
        userrolemapDao.remove(userrolemap);
    }
    
    public void deleteUserrolemapById(Integer idUserRoleMap){
        userrolemapDao.removeById(idUserRoleMap);
    }
    
    public List<Userrolemap> getAllUserrolemap(){
        return userrolemapDao.getAll();
    }
    
    public Userrolemap findUserrolemapById(Integer idUserRoleMap){
        return userrolemapDao.getById(idUserRoleMap);
    }
    
    public List<Userrolemap> findUserrolemapByUser(User user){
        return userrolemapDao.findByProperty("user", user);
    }
    
    public List<Userrolemap> findUserrolemapByRole(Role role){
        return userrolemapDao.findByProperty("role", role);
    }
    
    public List<Userrolemap> findUserrolemapByPropertys(String[] propertyNames, Object[] values){
        return userrolemapDao.findByPropertys(Userrolemap.class, propertyNames, values);
    }

    /**
     * @return the userrolemapDao
     */
    public UserrolemapDao getUserrolemapDao() {
        return userrolemapDao;
    }

    /**
     * @param userrolemapDao the userrolemapDao to set
     */
    public void setUserrolemapDao(UserrolemapDao userrolemapDao) {
        this.userrolemapDao = userrolemapDao;
    }
}
