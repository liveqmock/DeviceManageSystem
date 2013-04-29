/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.dbmanager.service.CustomtableService;
import org.dbmanager.service.CustomtablecolumnService;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 *
 * @author mac
 */
public class CustomtableManagerActionSupport extends ActionSupport {

    private CustomtableService customtableService;
    private String totalProperty;
    private List root;
    private CustomtablecolumnService customtablecolumnService;

    public CustomtableManagerActionSupport(){
        this.customtableService=new CustomtableService();
        this.customtablecolumnService = new CustomtablecolumnService();
    }
    
    public String execute() {
        return null;
    }

    public String loadDataToGridPanel() {
        List<Customtable> customtables = customtableService.getAllCustomtable();
        this.root = new ArrayList<Map>();
        for (Customtable customtable : customtables) {
            Map dataMap = new HashMap();
            dataMap.put("idCustomTable", customtable.getIdCustomTable());
            dataMap.put("name", customtable.getName());
            dataMap.put("showName", customtable.getShowName());
            dataMap.put("type", customtable.getType());
            dataMap.put("comment", customtable.getComment());
            this.root.add(dataMap);
        }
        return Action.SUCCESS;
    }

    public String updateDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtable customtable = new Customtable();
            customtable.setName(dataJSONObject.get("name").toString());
            customtable.setShowName(dataJSONObject.get("showName").toString());
            customtable.setType(Integer.parseInt(dataJSONObject.get("type").toString()));
            customtable.setComment(dataJSONObject.get("comment").toString());
            if (dataJSONObject.get("idCustomTable").toString().equals("null")) {
                customtable.setCustomtablecolumns(new HashSet<Customtablecolumn>(0));
                customtableService.addCustomtable(customtable);
            } else {
                customtable.setIdCustomTable(Integer.parseInt(dataJSONObject.get("idCustomTable").toString()));
                customtableService.updateCustomtable(customtable);
            }
        }
        return Action.SUCCESS;
    }

    public String deleteDataToDb() throws SQLException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtable customtable = customtableService.findCustomtableById(Integer.parseInt(dataJSONObject.get("idCustomTable").toString()));
            Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();
            if (customtablecolumns.size() == 0) {
                System.out.println("size is 0");
            } else {
                for (Customtablecolumn customtablecolumn : customtablecolumns) {
                    customtablecolumnService.deleteCustomtablecolumn(customtablecolumn);
                }
            }

            String dropString = null;
            dropString = "DROP TABLE IF EXISTS DeviceManagerDB." + customtable.getName() + ";";

            SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
            Connection connection = sessionFactory.getConnectionProvider().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(dropString);
            customtableService.deleteCustomtable(customtable);
            statement.close();
            connection.close();
        }
        return Action.SUCCESS;
    }

    /**
     * @return the totalProperty
     */
    public String getTotalProperty() {
        return totalProperty;
    }

    /**
     * @param totalProperty the totalProperty to set
     */
    public void setTotalProperty(String totalProperty) {
        this.totalProperty = totalProperty;
    }

    /**
     * @return the root
     */
    public List getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(List root) {
        this.root = root;
    }
}
