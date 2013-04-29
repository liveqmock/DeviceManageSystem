/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.service;

import java.util.List;
import org.dao.util.Page;
import org.hibernate.criterion.Criterion;
import org.systemmanager.dao.DepartmentDao;
import org.systemmanager.model.Department;

/**
 *
 * @author Administrator
 */
public class DepartmentService {
    private DepartmentDao departmentDao;

    public DepartmentService() {
        this.departmentDao=new DepartmentDao();
    }
    
    public void addDepartment(Department department){
        departmentDao.saveOrUpdate(department);
    }
    
    public void updateDepartment(Department department){
        departmentDao.update(department);
    }
    
    public void deleteDepartment(Department department){
        departmentDao.remove(department);
    }
    
    public void deleteDepartmentById(Integer idDepartment){
        departmentDao.removeById(idDepartment);
    }
    
    public List<Department> getAllDepartment(){
        return getDepartmentDao().getAll();
    }
    
    public Department findDepartmentById(Integer idDepartment){
        return departmentDao.getById(idDepartment);
    }
    
    public List<Department> findDepartmentByName(String name){
        return departmentDao.findByProperty("name", name);
    }

    public  List<Department> findDepartmentByLevel(int level){
        return departmentDao.findByProperty("level", level);
    }
    
    public Page pagedQueryDepartment(int pageNo, int pageSize, Criterion... criterions){
        return departmentDao.pagedQuery(Department.class, pageNo, pageSize, criterions);
    } 
    
    /**
     * @return the departmentDao
     */
    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    /**
     * @param departmentDao the departmentDao to set
     */
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }
    
}
