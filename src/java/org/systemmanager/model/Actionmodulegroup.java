package org.systemmanager.model;
// Generated 2013-4-14 3:21:50 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Actionmodulegroup generated by hbm2java
 */
public class Actionmodulegroup  implements java.io.Serializable {


     private Integer idActionModuleGroup;
     private String name;
     private Set<Actionmodule> actionmodules = new HashSet<Actionmodule>(0);

    public Actionmodulegroup() {
    }

	
    public Actionmodulegroup(String name) {
        this.name = name;
    }
    public Actionmodulegroup(String name, Set<Actionmodule> actionmodules) {
       this.name = name;
       this.actionmodules = actionmodules;
    }
   
    public Integer getIdActionModuleGroup() {
        return this.idActionModuleGroup;
    }
    
    public void setIdActionModuleGroup(Integer idActionModuleGroup) {
        this.idActionModuleGroup = idActionModuleGroup;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set<Actionmodule> getActionmodules() {
        return this.actionmodules;
    }
    
    public void setActionmodules(Set<Actionmodule> actionmodules) {
        this.actionmodules = actionmodules;
    }




}


