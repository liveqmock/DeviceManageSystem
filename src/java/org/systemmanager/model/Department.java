package org.systemmanager.model;
// Generated 2013-4-14 3:21:50 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Department generated by hbm2java
 */
public class Department  implements java.io.Serializable {


     private Integer idDepartment;
     private String name;
     private int level;
     private Set<Departmentmap> departmentmapsForIdDepartment = new HashSet<Departmentmap>(0);
     private Set<Departmentmap> departmentmapsForIdPreDepartment = new HashSet<Departmentmap>(0);
     private Set<User> users = new HashSet<User>(0);

    public Department() {
    }

	
    public Department(String name, int level) {
        this.name = name;
        this.level = level;
    }
    public Department(String name, int level, Set<Departmentmap> departmentmapsForIdDepartment, Set<Departmentmap> departmentmapsForIdPreDepartment, Set<User> users) {
       this.name = name;
       this.level = level;
       this.departmentmapsForIdDepartment = departmentmapsForIdDepartment;
       this.departmentmapsForIdPreDepartment = departmentmapsForIdPreDepartment;
       this.users = users;
    }
   
    public Integer getIdDepartment() {
        return this.idDepartment;
    }
    
    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    public Set<Departmentmap> getDepartmentmapsForIdDepartment() {
        return this.departmentmapsForIdDepartment;
    }
    
    public void setDepartmentmapsForIdDepartment(Set<Departmentmap> departmentmapsForIdDepartment) {
        this.departmentmapsForIdDepartment = departmentmapsForIdDepartment;
    }
    public Set<Departmentmap> getDepartmentmapsForIdPreDepartment() {
        return this.departmentmapsForIdPreDepartment;
    }
    
    public void setDepartmentmapsForIdPreDepartment(Set<Departmentmap> departmentmapsForIdPreDepartment) {
        this.departmentmapsForIdPreDepartment = departmentmapsForIdPreDepartment;
    }
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }




}


