/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dbmanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dbmanager.model.Commontablecolumn;
import org.dbmanager.service.CommontablecolumnService;

/**
 *
 * @author Administrator
 */
public class CommontablecolumnManagerActionSupport extends ActionSupport {
    private CommontablecolumnService commontablecolumnService;
    private ArrayList<Map> root;
    private String tobalProperty;
    
    public CommontablecolumnManagerActionSupport() {
        this.commontablecolumnService=new CommontablecolumnService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer tableType = Integer.parseInt(((String[]) params.get("tableType"))[0].toString());

        List<Commontablecolumn> commontablecolumns = commontablecolumnService.findCommontablecolumnByTableType(tableType);
        this.setRoot(new ArrayList<Map>());
        for (Commontablecolumn commontablecolumn : commontablecolumns) {
            Map dataMap = new HashMap();
            dataMap.put("idCommonTableColumn", commontablecolumn.getIdCommonTableColumn());       
            dataMap.put("columnName", commontablecolumn.getColumnName());
            dataMap.put("columnShowName", commontablecolumn.getColumnShowName());
            dataMap.put("columnIsHide", commontablecolumn.isColumnIsHide());
            dataMap.put("columnIsPrimaryKey", commontablecolumn.isColumnIsPrimaryKey());
            dataMap.put("columnType", commontablecolumn.getColumnType());
            dataMap.put("columnRestrict", commontablecolumn.getColumnRestrict());
            dataMap.put("columnDefualtValue", commontablecolumn.getColumnDefualtValue());
            dataMap.put("columnControlType", commontablecolumn.getColumnControlType());
            dataMap.put("columnControlDefine", commontablecolumn.getColumnControlDefine());
            dataMap.put("columnControlValueType", commontablecolumn.getColumnControlValueType());
            dataMap.put("tableType", commontablecolumn.getTableType());
            dataMap.put("comment", commontablecolumn.getComment());
            this.getRoot().add(dataMap);
        }
        
        return Action.SUCCESS;
    }
    
    public String updateDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer tableType = Integer.parseInt(((String[]) params.get("tableType"))[0].toString());

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Commontablecolumn commontablecolumn = new Commontablecolumn();
            commontablecolumn.setColumnName(dataJSONObject.get("columnName").toString());
            commontablecolumn.setColumnShowName(dataJSONObject.get("columnShowName").toString());
            commontablecolumn.setColumnIsHide(Boolean.parseBoolean(dataJSONObject.get("columnIsHide").toString()));
            commontablecolumn.setColumnIsPrimaryKey(Boolean.parseBoolean(dataJSONObject.get("columnIsPrimaryKey").toString()));
            commontablecolumn.setColumnType(dataJSONObject.get("columnType").toString());
            commontablecolumn.setColumnRestrict(dataJSONObject.get("columnRestrict").toString());
            commontablecolumn.setColumnDefualtValue(dataJSONObject.get("columnDefualtValue").toString());
            commontablecolumn.setColumnControlType(dataJSONObject.get("columnControlType").toString());
            commontablecolumn.setColumnControlDefine(dataJSONObject.get("columnControlDefine").toString());
            commontablecolumn.setColumnControlValueType(dataJSONObject.get("columnControlValueType").toString());
            commontablecolumn.setTableType(tableType);
            commontablecolumn.setComment(dataJSONObject.get("comment").toString());
            if (dataJSONObject.get("idCommonTableColumn").toString().equals("null")) {
                commontablecolumnService.addCommontablecolumn(commontablecolumn);
            } else {
                commontablecolumn.setIdCommonTableColumn(Integer.parseInt(dataJSONObject.get("idCommonTableColumn").toString()));
                commontablecolumnService.updateCommontablecolumn(commontablecolumn);
            }
        }

        return Action.SUCCESS;
    }
    
    public String deleteDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Commontablecolumn commontablecolumn = commontablecolumnService.findCommontablecolumnById(Integer.parseInt(dataJSONObject.get("idCommonTableColumn").toString()));
            commontablecolumnService.deleteCommontablecolumn(commontablecolumn);
        }

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
     * @return the tobalProperty
     */
    public String getTobalProperty() {
        return tobalProperty;
    }

    /**
     * @param tobalProperty the tobalProperty to set
     */
    public void setTobalProperty(String tobalProperty) {
        this.tobalProperty = tobalProperty;
    }
}
