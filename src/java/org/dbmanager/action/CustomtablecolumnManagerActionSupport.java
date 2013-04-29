/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.dbmanager.model.Commontablecolumn;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.dbmanager.service.CommontablecolumnService;
import org.dbmanager.service.CustomtableService;
import org.dbmanager.service.CustomtablecolumnService;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 *
 * @author Administrator
 */
public class CustomtablecolumnManagerActionSupport extends ActionSupport {

    private CustomtableService customtableService;
    private CustomtablecolumnService customtablecolumnService;
    private CommontablecolumnService commontablecolumnService;
    private String totalProperty;
    private List root;
    
    public CustomtablecolumnManagerActionSupport(){
        this.commontablecolumnService=new CommontablecolumnService();
        this.customtableService=new CustomtableService();
        this.customtablecolumnService=new CustomtablecolumnService();
    }

    public String execute() {
        return null;
    }

    public String loadDataToGridPanel() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);
        this.root = new ArrayList<Map>();
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            Map dataMap = new HashMap();
            dataMap.put("idCustomTableColumn", customtablecolumn.getIdCustomTableColumn());
            dataMap.put("idCustomTable", idCustomTable);
            dataMap.put("columnName", customtablecolumn.getColumnName());
            dataMap.put("columnShowName", customtablecolumn.getColumnShowName());
            dataMap.put("columnIsHide", customtablecolumn.isColumnIsHide());
            dataMap.put("columnIsPrimaryKey", customtablecolumn.isColumnIsPrimaryKey());
            dataMap.put("columnType", customtablecolumn.getColumnType());
            dataMap.put("columnRestrict", customtablecolumn.getColumnRestrict());
            dataMap.put("columnDefualtValue", customtablecolumn.getColumnDefualtValue());
            dataMap.put("columnControlType", customtablecolumn.getColumnControlType());
            dataMap.put("columnControlDefine", customtablecolumn.getColumnControlDefine());
            dataMap.put("columnControlValueType", customtablecolumn.getColumnControlValueType());
            dataMap.put("comment", customtablecolumn.getComment());
            this.root.add(dataMap);
        }

        return Action.SUCCESS;
    }

    public void loadDataToTreePanel() throws IOException {
        List<Customtable> customtables = customtableService.getAllCustomtable();
        String jsonString = null;
        for (Customtable customtable : customtables) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", customtable.getIdCustomTable());
            jSONObject.put("text", customtable.getShowName());
            jSONObject.put("name", customtable.getName());
            jSONObject.put("leaf", true);
            if (jsonString != null) {
                jsonString = jsonString + "," + jSONObject.toString();
            } else {
                jsonString = jSONObject.toString();
            }
        }
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        jsonString = "[" + jsonString + "]";
        PrintWriter out = response.getWriter();
        out.write(jsonString);
    }

    public String updateDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtablecolumn customtablecolumn = new Customtablecolumn();
            customtablecolumn.setCustomtable(customtable);
            customtablecolumn.setColumnName(dataJSONObject.get("columnName").toString());
            customtablecolumn.setColumnShowName(dataJSONObject.get("columnShowName").toString());
            customtablecolumn.setColumnIsHide(Boolean.parseBoolean(dataJSONObject.get("columnIsHide").toString()));
            customtablecolumn.setColumnIsPrimaryKey(Boolean.parseBoolean(dataJSONObject.get("columnIsPrimaryKey").toString()));
            customtablecolumn.setColumnType(dataJSONObject.get("columnType").toString());
            customtablecolumn.setColumnRestrict(dataJSONObject.get("columnRestrict").toString());
            customtablecolumn.setColumnDefualtValue(dataJSONObject.get("columnDefualtValue").toString());
            customtablecolumn.setColumnControlType(dataJSONObject.get("columnControlType").toString());
            customtablecolumn.setColumnControlDefine(dataJSONObject.get("columnControlDefine").toString());
            customtablecolumn.setColumnControlValueType(dataJSONObject.get("columnControlValueType").toString());
            customtablecolumn.setComment(dataJSONObject.get("comment").toString());
            if (dataJSONObject.get("idCustomTableColumn").toString().equals("null")) {
                customtablecolumnService.addCustomtablecolumn(customtablecolumn);
                customtablecolumns.add(customtablecolumn);
            } else {
                customtablecolumn.setIdCustomTableColumn(Integer.parseInt(dataJSONObject.get("idCustomTableColumn").toString()));
                customtablecolumnService.updateCustomtablecolumn(customtablecolumn);
            }
        }

        customtable.setCustomtablecolumns(customtablecolumns);
        return Action.SUCCESS;
    }

    public String deleteDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtablecolumn customtablecolumn = customtablecolumnService.findCustomtablecolumnById(Integer.parseInt(dataJSONObject.get("idCustomTableColumn").toString()));
            customtablecolumnService.deleteCustomtablecolumn(customtablecolumn);
            customtablecolumns.remove(customtablecolumn);
        }

        customtable.setCustomtablecolumns(customtablecolumns);
        return Action.SUCCESS;
    }

    public void buildCustomtable() throws SQLException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();

        String columnsString = null;
        ArrayList<String> primaryKeyColumnNames = new ArrayList<String>();
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (columnsString == null) {
                if ("null".equals(customtablecolumn.getColumnDefualtValue()) || "".equals(customtablecolumn.getColumnDefualtValue())) {
                    columnsString = customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict();
                } else {
                    columnsString = customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict() + " DEFAULT " + customtablecolumn.getColumnDefualtValue();
                }
            } else {
                if ("null".equals(customtablecolumn.getColumnDefualtValue()) || "".equals(customtablecolumn.getColumnDefualtValue())) {
                    columnsString = columnsString + ",\n" + customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict();
                } else {
                    columnsString = columnsString + ",\n" + customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict() + " DEFAULT " + customtablecolumn.getColumnDefualtValue();
                }
            }
            if (customtablecolumn.isColumnIsPrimaryKey()) {
                primaryKeyColumnNames.add(customtablecolumn.getColumnName());
            }
        }
        String primaryKeyString = "";
        if (!primaryKeyColumnNames.isEmpty()) {
            String primaryKeyNamesString = null;
            for (String primaryKeyColumnName : primaryKeyColumnNames) {
                if (primaryKeyNamesString == null) {
                    primaryKeyNamesString = primaryKeyColumnName.toString();
                } else {
                    primaryKeyNamesString = primaryKeyNamesString + ",\n" + primaryKeyColumnName.toString();
                }
            }
            primaryKeyString = ",\nPRIMARY KEY (" + primaryKeyNamesString + ")";

        }
        String createString = null;
        String dropString = null;
        dropString = "DROP TABLE IF EXISTS DeviceManagerDB." + customtable.getName() + ";\n";
        createString = "CREATE TABLE IF NOT EXISTS DeviceManagerDB." + customtable.getName() + "\n("
                + columnsString + primaryKeyString + ")";

        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(dropString);
        statement.execute(createString);
        statement.close();
        connection.close();

    }

    public void getSQL() throws SQLException, IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();

        String columnsString = null;
        ArrayList<String> primaryKeyColumnNames = new ArrayList<String>();
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (columnsString == null) {
                if ("null".equals(customtablecolumn.getColumnDefualtValue()) || "".equals(customtablecolumn.getColumnDefualtValue())) {
                    columnsString = customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict();
                } else {
                    columnsString = customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict() + " DEFAULT " + customtablecolumn.getColumnDefualtValue();
                }
            } else {
                if ("null".equals(customtablecolumn.getColumnDefualtValue()) || "".equals(customtablecolumn.getColumnDefualtValue())) {
                    columnsString = columnsString + ",\n" + customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict();
                } else {
                    columnsString = columnsString + ",\n" + customtablecolumn.getColumnName() + " " + customtablecolumn.getColumnType() + " " + customtablecolumn.getColumnRestrict() + " DEFAULT " + customtablecolumn.getColumnDefualtValue();
                }
            }
            if (customtablecolumn.isColumnIsPrimaryKey()) {
                primaryKeyColumnNames.add(customtablecolumn.getColumnName());
            }
        }
        String primaryKeyString = "";
        if (!primaryKeyColumnNames.isEmpty()) {
            String primaryKeyNamesString = null;
            for (String primaryKeyColumnName : primaryKeyColumnNames) {
                if (primaryKeyNamesString == null) {
                    primaryKeyNamesString = primaryKeyColumnName.toString();
                } else {
                    primaryKeyNamesString = primaryKeyNamesString + ",\n" + primaryKeyColumnName.toString();
                }
            }
            primaryKeyString = ",\nPRIMARY KEY (" + primaryKeyNamesString + ")";

        }
        String createString = null;
        String dropString = null;
        dropString = "DROP TABLE IF EXISTS DeviceManagerDB." + customtable.getName() + ";\n";
        createString = "CREATE TABLE IF NOT EXISTS DeviceManagerDB." + customtable.getName() + "\n("
                + columnsString + primaryKeyString + ")";

        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        String sql = dropString + createString;
        PrintWriter out = response.getWriter();
        out.write(sql);
    }

    public String loadCommontablecolumn() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        int tableType = customtable.getType();

        Set<Customtablecolumn> customtablecolumns = customtable.getCustomtablecolumns();
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            customtablecolumnService.deleteCustomtablecolumn(customtablecolumn);
        }

        List<Commontablecolumn> commontablecolumns = commontablecolumnService.findCommontablecolumnByTableType(tableType);
        for (Commontablecolumn commontablecolumn : commontablecolumns) {
            Customtablecolumn customtablecolumn = new Customtablecolumn();
            customtablecolumn.setIdCustomTableColumn(null);
            customtablecolumn.setCustomtable(customtable);
            customtablecolumn.setColumnName(commontablecolumn.getColumnName());
            customtablecolumn.setColumnShowName(commontablecolumn.getColumnShowName());
            customtablecolumn.setColumnIsHide(commontablecolumn.isColumnIsHide());
            customtablecolumn.setColumnIsPrimaryKey(commontablecolumn.isColumnIsPrimaryKey());     
            customtablecolumn.setColumnType(commontablecolumn.getColumnType());
            customtablecolumn.setColumnRestrict(commontablecolumn.getColumnRestrict());
            customtablecolumn.setColumnDefualtValue(commontablecolumn.getColumnDefualtValue());
            customtablecolumn.setColumnControlType(commontablecolumn.getColumnControlType());
            customtablecolumn.setColumnControlDefine(commontablecolumn.getColumnControlDefine());
            customtablecolumn.setColumnControlValueType(commontablecolumn.getColumnControlValueType());
            customtablecolumn.setComment(commontablecolumn.getComment());
            customtablecolumnService.addCustomtablecolumn(customtablecolumn);
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
