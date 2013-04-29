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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.dbmanager.model.Customqueryaction;
import org.dbmanager.model.Customquerysubaction;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.dbmanager.model.Customtablequeryactionmap;
import org.dbmanager.service.CustomqueryactionService;
import org.dbmanager.service.CustomquerysubactionService;
import org.dbmanager.service.CustomtableService;
import org.dbmanager.service.CustomtablecolumnService;
import org.dbmanager.service.CustomtablequeryactionmapService;

/**
 *
 * @author Administrator
 */
public class CustomtablequeryactioneditManagerActionSupport extends ActionSupport {
    
    private CustomtableService customtableService;
    private CustomtablecolumnService customtablecolumnService;
    private CustomqueryactionService customqueryactionService;
    private CustomquerysubactionService customquerysubactionService;
    private CustomtablequeryactionmapService customtablequeryactionmapService;
    private String totalProperty;
    private List root;
    
    public CustomtablequeryactioneditManagerActionSupport() {
        this.customtableService=new CustomtableService();
        this.customtablecolumnService=new CustomtablecolumnService();
        this.customqueryactionService = new CustomqueryactionService();
        this.customquerysubactionService = new CustomquerysubactionService();
        this.customtablequeryactionmapService = new CustomtablequeryactionmapService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomQueryAction = Integer.parseInt(((String[]) params.get("idCustomQueryAction"))[0].toString());

        Customqueryaction customqueryaction = this.customqueryactionService.findCustomqueryactionById(idCustomQueryAction);
        List<Customquerysubaction> customquerysubactions = this.customquerysubactionService.findCustomquerysubactionByCustomqueryaction(customqueryaction);
        this.setRoot(new ArrayList<Map>());
        for (Customquerysubaction customquerysubaction : customquerysubactions) {
            Map dataMap = new HashMap();
            dataMap.put("idCustomQuerySubAction", customquerysubaction.getIdCustomQuerySubAction());
            dataMap.put("idCustomQueryAction", idCustomQueryAction);
            dataMap.put("property", customquerysubaction.getProperty());
            dataMap.put("querycase", customquerysubaction.getQuerycase());
            dataMap.put("queryvalue", customquerysubaction.getQueryvalue());
            dataMap.put("subactionrelation", customquerysubaction.getSubactionrelation());
            this.getRoot().add(dataMap);
        }

        return Action.SUCCESS;
    }

    public void loadDataToTreePanel() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        
        List<Customtablequeryactionmap> customtablequeryactionmaps = this.customtablequeryactionmapService.findCustomtablequeryactionmapByCustomtable(idCustomTable);
        String jsonString = null;
        for (Customtablequeryactionmap customtablequeryactionmap : customtablequeryactionmaps) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", customtablequeryactionmap.getIdCustomQueryAction());
            jSONObject.put("text", this.customqueryactionService.findCustomqueryactionById(customtablequeryactionmap.getIdCustomQueryAction()).getName());
            jSONObject.put("name", this.customqueryactionService.findCustomqueryactionById(customtablequeryactionmap.getIdCustomQueryAction()).getName());
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
    
    public String loadCustomtablecolumnNames() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        this.root = new ArrayList<Map>();

        Customtable customtable = this.customtableService.findCustomtableById(idCustomTable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            Map dataMap = new HashMap();
            dataMap.put("columnName", customtablecolumn.getColumnName());
            dataMap.put("columnShowName", customtablecolumn.getColumnShowName());
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
        Integer idCustomQueryAction = Integer.parseInt(((String[]) params.get("idCustomQueryAction"))[0].toString());
        Customqueryaction customqueryaction = this.customqueryactionService.findCustomqueryactionById(idCustomQueryAction);
        Set<Customquerysubaction> customquerysubactions=customqueryaction.getCustomquerysubactions();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customquerysubaction customquerysubaction = new Customquerysubaction();
            customquerysubaction.setCustomqueryaction(customqueryaction);
            customquerysubaction.setProperty(dataJSONObject.get("property").toString());
            customquerysubaction.setQuerycase(dataJSONObject.get("querycase").toString());
            customquerysubaction.setQueryvalue(dataJSONObject.get("queryvalue").toString());
            customquerysubaction.setSubactionrelation(dataJSONObject.get("subactionrelation").toString());
            if (dataJSONObject.get("idCustomQuerySubAction").toString().equals("null")) {
                this.customquerysubactionService.addCustomquerysubaction(customquerysubaction);
                customquerysubactions.add(customquerysubaction);
            } else {
                customquerysubaction.setIdCustomQuerySubAction(Integer.parseInt(dataJSONObject.get("idCustomQuerySubAction").toString()));
                this.customquerysubactionService.updateCustomquerysubaction(customquerysubaction);
            }
        }

        customqueryaction.setCustomquerysubactions(customquerysubactions);
        return Action.SUCCESS;
    }

    public String deleteDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idCustomQueryAction = Integer.parseInt(((String[]) params.get("idCustomQueryAction"))[0].toString());
        Customqueryaction customqueryaction = this.customqueryactionService.findCustomqueryactionById(idCustomQueryAction);
        Set<Customquerysubaction> customquerysubactions = customqueryaction.getCustomquerysubactions();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customquerysubaction actionmodule = this.customquerysubactionService.findCustomquerysubactionById(Integer.parseInt(dataJSONObject.get("idCustomQuerySubAction").toString()));
            this.customquerysubactionService.deleteCustomquerysubaction(actionmodule);
            customquerysubactions.remove(actionmodule);
        }

        customqueryaction.setCustomquerysubactions(customquerysubactions);
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
