package org.dbmanager.model;
// Generated 2013-4-23 23:15:29 by Hibernate Tools 3.2.1.GA



/**
 * Customquerysubaction generated by hbm2java
 */
public class Customquerysubaction  implements java.io.Serializable {


     private Integer idCustomQuerySubAction;
     private Customqueryaction customqueryaction;
     private String property;
     private String querycase;
     private String queryvalue;
     private String subactionrelation;

    public Customquerysubaction() {
    }

	
    public Customquerysubaction(Customqueryaction customqueryaction) {
        this.customqueryaction = customqueryaction;
    }
    public Customquerysubaction(Customqueryaction customqueryaction, String property, String querycase, String queryvalue, String subactionrelation) {
       this.customqueryaction = customqueryaction;
       this.property = property;
       this.querycase = querycase;
       this.queryvalue = queryvalue;
       this.subactionrelation = subactionrelation;
    }
   
    public Integer getIdCustomQuerySubAction() {
        return this.idCustomQuerySubAction;
    }
    
    public void setIdCustomQuerySubAction(Integer idCustomQuerySubAction) {
        this.idCustomQuerySubAction = idCustomQuerySubAction;
    }
    public Customqueryaction getCustomqueryaction() {
        return this.customqueryaction;
    }
    
    public void setCustomqueryaction(Customqueryaction customqueryaction) {
        this.customqueryaction = customqueryaction;
    }
    public String getProperty() {
        return this.property;
    }
    
    public void setProperty(String property) {
        this.property = property;
    }
    public String getQuerycase() {
        return this.querycase;
    }
    
    public void setQuerycase(String querycase) {
        this.querycase = querycase;
    }
    public String getQueryvalue() {
        return this.queryvalue;
    }
    
    public void setQueryvalue(String queryvalue) {
        this.queryvalue = queryvalue;
    }
    public String getSubactionrelation() {
        return this.subactionrelation;
    }
    
    public void setSubactionrelation(String subactionrelation) {
        this.subactionrelation = subactionrelation;
    }




}


