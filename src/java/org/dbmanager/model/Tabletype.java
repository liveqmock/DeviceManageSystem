package org.dbmanager.model;
// Generated 2013-4-23 23:15:29 by Hibernate Tools 3.2.1.GA



/**
 * Tabletype generated by hbm2java
 */
public class Tabletype  implements java.io.Serializable {


     private Integer idTableType;
     private Integer tableType;
     private String tableTypeName;
     private String comment;

    public Tabletype() {
    }

    public Tabletype(Integer tableType, String tableTypeName, String comment) {
       this.tableType = tableType;
       this.tableTypeName = tableTypeName;
       this.comment = comment;
    }
   
    public Integer getIdTableType() {
        return this.idTableType;
    }
    
    public void setIdTableType(Integer idTableType) {
        this.idTableType = idTableType;
    }
    public Integer getTableType() {
        return this.tableType;
    }
    
    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }
    public String getTableTypeName() {
        return this.tableTypeName;
    }
    
    public void setTableTypeName(String tableTypeName) {
        this.tableTypeName = tableTypeName;
    }
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }




}


