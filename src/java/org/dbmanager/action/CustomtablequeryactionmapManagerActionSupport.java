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
import org.dbmanager.model.Customtablequeryactionmap;
import org.dbmanager.service.CustomqueryactionService;
import org.dbmanager.service.CustomquerysubactionService;
import org.dbmanager.service.CustomtablequeryactionmapService;
import org.systemmanager.model.Actionmodulegroup;

/**
 *
 * @author Administrator
 */
public class CustomtablequeryactionmapManagerActionSupport extends ActionSupport {

    private CustomqueryactionService customqueryactionService;
    private CustomquerysubactionService customquerysubactionService;
    private CustomtablequeryactionmapService customtablequeryactionmapService;
    private String totalProperty;
    private List root;

    public CustomtablequeryactionmapManagerActionSupport() {
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
        Integer idCustomTable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        List<Customtablequeryactionmap> customtablequeryactionmaps = this.customtablequeryactionmapService.findCustomtablequeryactionmapByCustomtable(idCustomTable);
        this.setRoot(new ArrayList<Map>());

        for (Customtablequeryactionmap customtablequeryactionmap : customtablequeryactionmaps) {
            Map dataMap = new HashMap();
            dataMap.put("idCustomTableQueryActionMap", customtablequeryactionmap.getIdCustomTableQueryActionMap());
            dataMap.put("idCustomTable", customtablequeryactionmap.getIdCustomTable());
            dataMap.put("idCustomQueryAction", customtablequeryactionmap.getIdCustomQueryAction());
            dataMap.put("name", this.customqueryactionService.findCustomqueryactionById(customtablequeryactionmap.getIdCustomQueryAction()).getName());
            dataMap.put("comment", this.customqueryactionService.findCustomqueryactionById(customtablequeryactionmap.getIdCustomQueryAction()).getComment());
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

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
 
            Customqueryaction customqueryaction = new Customqueryaction();
            customqueryaction.setName(dataJSONObject.get("name").toString());
            customqueryaction.setComment(dataJSONObject.get("comment").toString());
            if (dataJSONObject.get("idCustomQueryAction").toString().equals("null")) {
                this.customqueryactionService.addCustomqueryaction(customqueryaction);
            } else {
                customqueryaction.setIdCustomQueryAction(Integer.parseInt(dataJSONObject.get("idCustomQueryAction").toString()));
                this.customqueryactionService.updateCustomqueryaction(customqueryaction);
            }
            
            Customtablequeryactionmap customtablequeryactionmap = new Customtablequeryactionmap();
            customtablequeryactionmap.setIdCustomTable(Integer.parseInt(dataJSONObject.get("idCustomTable").toString()));
            customtablequeryactionmap.setIdCustomQueryAction(customqueryaction.getIdCustomQueryAction());
            if (dataJSONObject.get("idCustomTableQueryActionMap").toString().equals("null")) {
                this.customtablequeryactionmapService.addCustomtablequeryactionmap(customtablequeryactionmap);
            } else {
                customtablequeryactionmap.setIdCustomTableQueryActionMap(Integer.parseInt(dataJSONObject.get("idCustomTableQueryActionMap").toString()));
                this.customtablequeryactionmapService.updateCustomtablequeryactionmap(customtablequeryactionmap);
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
            Customtablequeryactionmap customtablequeryactionmap=this.customtablequeryactionmapService.findCustomtablequeryactionmapById(Integer.parseInt(dataJSONObject.get("idCustomTableQueryActionMap").toString()));
            Customqueryaction customqueryaction=this.customqueryactionService.findCustomqueryactionById(Integer.parseInt(dataJSONObject.get("idCustomTableQueryActionMap").toString()));
            this.customtablequeryactionmapService.deleteCustomtablequeryactionmap(customtablequeryactionmap);
            Set<Customquerysubaction> customquerysubactions=customqueryaction.getCustomquerysubactions();
            for (Customquerysubaction customquerysubaction : customquerysubactions) {
                this.customquerysubactionService.deleteCustomquerysubaction(customquerysubaction);
            }
            this.customqueryactionService.deleteCustomqueryaction(customqueryaction);
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
