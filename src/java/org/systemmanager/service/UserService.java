/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.hibernate.criterion.Criterion;
import org.systemmanager.dao.UserDao;
import org.systemmanager.model.Department;
import org.systemmanager.model.User;

/**
 *
 * @author Administrator
 */
public class UserService{
    private UserDao userDao;
    
    public UserService() {
        this.userDao=new UserDao();
    }

    public void addUser(User user){
        userDao.saveOrUpdate(user);
    }

    public void updateUser(User user){
        userDao.update(user);
    }
    
    public void deleteUser(User user){
        userDao.remove(user);
    }
    
    public void deleteUserById(Integer idUser){
        userDao.removeById(idUser);
    }
 
    public List<User> getAllUser(){
        return userDao.getAll();
    }
    
    public User findUserById(Integer idUser){
        return userDao.getById(idUser);
    }
    
    public List<User> findUserByUserame(String username){
        return userDao.findByProperty("username", username);
    }
    
    public List<User> findUserByDepartment(Department department){
        return userDao.findByProperty("department", department);
    }
    
    public List<User> findUserByPropertys(String[] propertyNames, Object[] values){
        return userDao.findByPropertys(User.class, propertyNames, values);
    }
    
    public Page pagedQueryUser(int pageNo, int pageSize, Criterion... criterions){
        return userDao.pagedQuery(User.class, pageNo, pageSize, criterions);
    }
    /**
     * @return the userDao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao the userDao to set
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    
}
