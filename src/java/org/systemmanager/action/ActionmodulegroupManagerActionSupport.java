/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.systemmanager.model.Actionmodule;
import org.systemmanager.model.Actionmodulegroup;
import org.systemmanager.service.ActionmodulegroupService;

/**
 *
 * @author Administrator
 */
public class ActionmodulegroupManagerActionSupport extends ActionSupport {
    
    private ActionmodulegroupService actionmodulegroupService;
    private String totalProperty;
    private List root;
    
    public ActionmodulegroupManagerActionSupport() {
        this.actionmodulegroupService=new ActionmodulegroupService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        List<Actionmodulegroup> actionmodulegroups = this.actionmodulegroupService.getAllActionmodulegroup();
        this.setRoot(new ArrayList<Map>());
        for (Actionmodulegroup actionmodulegroup : actionmodulegroups) {
            Map dataMap = new HashMap();
            dataMap.put("idActionModuleGroup", actionmodulegroup.getIdActionModuleGroup());
            dataMap.put("name", actionmodulegroup.getName());
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
            Actionmodulegroup actionmodulegroup = new Actionmodulegroup();
            actionmodulegroup.setName(dataJSONObject.get("name").toString());
            if (dataJSONObject.get("idActionModuleGroup").toString().equals("null")) {
                actionmodulegroup.setActionmodules(new HashSet<Actionmodule>(0));
                this.actionmodulegroupService.addActionmodulegroup(actionmodulegroup);
            } else {
                actionmodulegroup.setIdActionModuleGroup(Integer.parseInt(dataJSONObject.get("idActionModuleGroup").toString()));
                this.actionmodulegroupService.updateActionmodulegroup(actionmodulegroup);
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
            Actionmodulegroup actionmodulegroup = this.actionmodulegroupService.findActionmodulegroupById(Integer.parseInt(dataJSONObject.get("idActionModuleGroup").toString()));
            Set<Actionmodule> actionmodules = actionmodulegroup.getActionmodules();
            if (actionmodules.size() == 0) {
                System.out.println("size is 0");
            } else {
                for (Actionmodule actionmodule : actionmodules) {
                    actionmodule.setActionmodulegroup(null);
                }
            }
            this.actionmodulegroupService.deleteActionmodulegroup(actionmodulegroup);
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
