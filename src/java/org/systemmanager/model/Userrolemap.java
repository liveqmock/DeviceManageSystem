package org.systemmanager.model;
// Generated 2013-4-14 3:21:50 by Hibernate Tools 3.2.1.GA



/**
 * Userrolemap generated by hbm2java
 */
public class Userrolemap  implements java.io.Serializable {


     private Integer idUserRoleMap;
     private User user;
     private Role role;
     private String date;

    public Userrolemap() {
    }

    public Userrolemap(User user, Role role, String date) {
       this.user = user;
       this.role = role;
       this.date = date;
    }
   
    public Integer getIdUserRoleMap() {
        return this.idUserRoleMap;
    }
    
    public void setIdUserRoleMap(Integer idUserRoleMap) {
        this.idUserRoleMap = idUserRoleMap;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }




}

