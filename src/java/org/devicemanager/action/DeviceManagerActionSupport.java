/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devicemanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.dbmanager.service.CustomtableService;
import org.dbmanager.service.CustomtablecolumnService;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 *
 * @author Administrator
 */
public class DeviceManagerActionSupport extends ActionSupport {

    private CustomtableService customtableService;
    private CustomtablecolumnService customtablecolumnService;
    private int totalProperty;
    private ArrayList<Map> root;

    public DeviceManagerActionSupport(){
        this.customtableService=new CustomtableService();
        this.customtablecolumnService=new CustomtablecolumnService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadDataToTreePanel() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer customtabletype = Integer.parseInt(((String[]) params.get("customtabletype"))[0].toString());

        List<Customtable> customtables = customtableService.findCustomtableByType(customtabletype);
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

    public void loadGridColumn() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        String json = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (!customtablecolumn.isColumnIsHide()) {
                if (json == null) {
                    json = "{header:\"" + customtablecolumn.getColumnShowName() + "\",dataIndex:\"" + customtablecolumn.getColumnName() + "\",sortable:true,width:80,editor:new Ext.grid.GridEditor(new Ext.form." + customtablecolumn.getColumnControlType() + "(" + customtablecolumn.getColumnControlDefine() + "))}";
                } else {
                    json = json + ",\n{header:\"" + customtablecolumn.getColumnShowName() + "\",dataIndex:\"" + customtablecolumn.getColumnName() + "\",sortable:true,width:80,editor:new Ext.grid.GridEditor(new Ext.form." + customtablecolumn.getColumnControlType() + "(" + customtablecolumn.getColumnControlDefine() + "))}";
                }
            }
        }

        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    public void loadGridRecord() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        String json = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (json == null) {
                json = "{name:\"" + customtablecolumn.getColumnName() + "\",type:\"" + customtablecolumn.getColumnControlValueType() + "\",mapping:\"" + customtablecolumn.getColumnName() + "\"}";
            } else {
                json = json + ",\n{name:\"" + customtablecolumn.getColumnName() + "\",type:\"" + customtablecolumn.getColumnControlValueType() + "\",mapping:\"" + customtablecolumn.getColumnName() + "\"}";
            }
        }

        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    public String loadGridData() throws SQLException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        int start = Integer.parseInt(((String[]) params.get("start"))[0].toString());
        int limit = Integer.parseInt(((String[]) params.get("limit"))[0].toString());
        String columnName = ((String[]) params.get("columnName"))[0].toString();
        String searchValue = ((String[]) params.get("searchValue"))[0].toString();

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);
        this.setRoot(new ArrayList<Map>());

        String queryString = "select * from devicemanagerdb." + customtable.getName();

        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();

        if (columnName==null||columnName=="") {
            
        }else{
            String queryCaseString = columnName + " like " + "'%" + searchValue + "%'";
            queryString = queryString + " where " + queryCaseString;
        }

        System.out.print(queryString);

        ResultSet resultSet = statement.executeQuery(queryString);
        int count = 0;
        while (resultSet.next()&&count<start+limit) {
            if(count<start){
                count++;
            }else{
                Map dataMap = new HashMap();
            for (Customtablecolumn customtablecolumn : customtablecolumns) {
                String columnValueType = customtablecolumn.getColumnControlValueType();
                if ("int".equals(columnValueType)) {
                    dataMap.put(customtablecolumn.getColumnName(), resultSet.getInt(customtablecolumn.getColumnName()));
                } else if ("string".equals(columnValueType)) {
                    dataMap.put(customtablecolumn.getColumnName(), resultSet.getString(customtablecolumn.getColumnName()));
                } else if ("date".equals(columnValueType)) {
                    dataMap.put(customtablecolumn.getColumnName(), resultSet.getDate(customtablecolumn.getColumnName()));
                } else if ("boolean".equals(columnValueType)) {
                    dataMap.put(customtablecolumn.getColumnName(), resultSet.getBoolean(customtablecolumn.getColumnName()));
                }
            }
            this.getRoot().add(dataMap);
            count++;
            }     
        }   
        resultSet.last();
        this.totalProperty=resultSet.getRow();
        statement.close();
        connection.close();

        return Action.SUCCESS;
    }

    public void createInitValue() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        String json = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (json == null) {
                json = customtablecolumn.getColumnName() + ":null";
            } else {
                json = json + "," + customtablecolumn.getColumnName() + ":null";
            }
        }
        json = "{" + json + "}";
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        out.write(json);
    }

    public String updateDataToDb() throws SQLException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键   

        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();

        String sql = null;
        String primaryKeyColumnName = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (customtablecolumn.isColumnIsPrimaryKey()) {
                primaryKeyColumnName = customtablecolumn.getColumnName();
                break;
            }
        }

        for (Object dataRecord : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(dataRecord);
            String columns = null;
            String values = null;
            Object o = dataJSONObject.get(primaryKeyColumnName);      
            if (o.equals(null)) {
                for (Customtablecolumn customtablecolumn : customtablecolumns) {
                    Object value=dataJSONObject.get(customtablecolumn.getColumnName());
                    String valueType=customtablecolumn.getColumnControlValueType();
                    String columnName=customtablecolumn.getColumnName();
                    String columnControlType=customtablecolumn.getColumnControlType();
                    
                    if (columns == null) {
                        columns = columnName;
                    } else {
                        columns = columns + "," + columnName;
                    }
                    if (values == null) {
                        if (valueType.equals("string")|| valueType.equals("date")) {  
                            if(columnControlType.equals("DateField")&&value.equals("null")){
                                values = value.toString();
                            }else{
                                values = "'" + value.toString() + "'";
                            }
                        } else {
                            values = value.toString();
                        }
                    } else {
                        if (valueType.equals("string")|| valueType.equals("date")) {
                            if(columnControlType.equals("DateField")&&value.equals("null")){
                                values = values + "," +value.toString();
                            }else{
                                values = values + "," +"'" + value.toString() + "'";
                            }
                        } else {
                            values = values + "," + value.toString();
                        }
                    }
                }
                sql = "INSERT INTO devicemanagerdb." + customtable.getName() + " (" + columns + ") VALUES (" + values + ");";
                statement.executeUpdate(sql);
                connection.commit();
            } else {
                for (Customtablecolumn customtablecolumn : customtablecolumns) {
                    Object value=dataJSONObject.get(customtablecolumn.getColumnName());
                    String valueType=customtablecolumn.getColumnControlValueType();
                    String columnName=customtablecolumn.getColumnName();
                    String columnControlType=customtablecolumn.getColumnControlType();
                    
                    if (columns == null) {
                        if (valueType.equals("string")|| valueType.equals("date")) {
                            if(columnControlType.equals("DateField")&&(value.equals("null")||value.equals(""))){
                                columns = columnName + "=null";
                            }else{
                                columns = columnName + "='" + value.toString() + "'";
                            }
                        } else {
                            columns = columnName + "=" + value.toString();
                        }
                    } else {
                        if (valueType.equals("string")|| valueType.equals("date")) {
                            if(columnControlType.equals("DateField")&&(value.equals("null")||value.equals(""))){
                                columns = columns + "," + columnName + "=null";
                            }else{
                                columns = columns + "," + columnName + "='" + value.toString() + "'";
                            }
                        } else {
                            columns = columns + "," + columnName + "=" + value.toString();
                        }
                    }
                }
                sql = "UPDATE devicemanagerdb." + customtable.getName() + " SET " + columns + " WHERE " + primaryKeyColumnName + "=" + dataJSONObject.get(primaryKeyColumnName);
                statement.executeUpdate(sql);
                connection.commit();
            }
        }
        statement.close();
        connection.close();

        return Action.SUCCESS;
    }

    public String deleteDataToDb() throws SQLException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        Customtable customtable = customtableService.findCustomtableById(idCustomTable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);
        
        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();
        
        String primaryKeyColumnName = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (customtablecolumn.isColumnIsPrimaryKey()) {
                primaryKeyColumnName = customtablecolumn.getColumnName();
                break;
            }
        }
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            String primaryKeyValue=dataJSONObject.get(primaryKeyColumnName).toString();
            String sql="DELETE FROM devicemanagerdb."+customtable.getName()+" WHERE "+primaryKeyColumnName+"="+primaryKeyValue;
            statement.execute(sql);
            connection.commit();
        }        
        statement.close();
        connection.close();
        
        return Action.SUCCESS;
    }

    /**
     * @return the root
     */
    public ArrayList<Map> getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(ArrayList<Map> root) {
        this.root = root;
    }

    /**
     * @return the totalProperty
     */
    public int getTotalProperty() {
        return totalProperty;
    }

    /**
     * @param totalProperty the totalProperty to set
     */
    public void setTotalProperty(int totalProperty) {
        this.totalProperty = totalProperty;
    }
}
