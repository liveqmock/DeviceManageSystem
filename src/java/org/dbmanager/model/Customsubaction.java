package org.dbmanager.model;
// Generated 2013-4-23 23:15:29 by Hibernate Tools 3.2.1.GA



/**
 * Customsubaction generated by hbm2java
 */
public class Customsubaction  implements java.io.Serializable {


     private Integer idCustomSubAction;
     private Customaction customaction;
     private String subSql;
     private String subValue;
     private String inputShowName;

    public Customsubaction() {
    }

	
    public Customsubaction(Customaction customaction) {
        this.customaction = customaction;
    }
    public Customsubaction(Customaction customaction, String subSql, String subValue, String inputShowName) {
       this.customaction = customaction;
       this.subSql = subSql;
       this.subValue = subValue;
       this.inputShowName = inputShowName;
    }
   
    public Integer getIdCustomSubAction() {
        return this.idCustomSubAction;
    }
    
    public void setIdCustomSubAction(Integer idCustomSubAction) {
        this.idCustomSubAction = idCustomSubAction;
    }
    public Customaction getCustomaction() {
        return this.customaction;
    }
    
    public void setCustomaction(Customaction customaction) {
        this.customaction = customaction;
    }
    public String getSubSql() {
        return this.subSql;
    }
    
    public void setSubSql(String subSql) {
        this.subSql = subSql;
    }
    public String getSubValue() {
        return this.subValue;
    }
    
    public void setSubValue(String subValue) {
        this.subValue = subValue;
    }
    public String getInputShowName() {
        return this.inputShowName;
    }
    
    public void setInputShowName(String inputShowName) {
        this.inputShowName = inputShowName;
    }




}


