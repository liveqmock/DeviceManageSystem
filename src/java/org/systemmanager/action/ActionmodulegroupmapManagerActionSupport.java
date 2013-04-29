/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.action;

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
import org.systemmanager.model.Actionmodule;
import org.systemmanager.model.Actionmodulegroup;
import org.systemmanager.service.ActionmoduleService;
import org.systemmanager.service.ActionmodulegroupService;

/**
 *
 * @author Administrator
 */
public class ActionmodulegroupmapManagerActionSupport extends ActionSupport {
    
    private ActionmoduleService actionmoduleService;
    private ActionmodulegroupService actionmodulegroupService;
    private String totalProperty;
    private List root;
    
    public ActionmodulegroupmapManagerActionSupport() {
        this.actionmoduleService=new ActionmoduleService();
        this.actionmodulegroupService=new ActionmodulegroupService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idActionModuleGroup = Integer.parseInt(((String[]) params.get("idActionModuleGroup"))[0].toString());

        Actionmodulegroup actionmodulegroup = this.actionmodulegroupService.findActionmodulegroupById(idActionModuleGroup);
        List<Actionmodule> actionmodules = this.actionmoduleService.findActionmoduleByActionmodulegroup(actionmodulegroup);
        this.setRoot(new ArrayList<Map>());
        for (Actionmodule actionmodule : actionmodules) {
            Map dataMap = new HashMap();
            dataMap.put("idActionModule", actionmodule.getIdActionModule());
            dataMap.put("name", actionmodule.getName());
            dataMap.put("url", actionmodule.getUrl());
            this.getRoot().add(dataMap);
        }

        return Action.SUCCESS;
    }

    public void loadDataToTreePanel() throws IOException {
        List<Actionmodulegroup> actionmodulegroups = this.actionmodulegroupService.getAllActionmodulegroup();
        String jsonString = null;
        for (Actionmodulegroup actionmodulegroup : actionmodulegroups) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", actionmodulegroup.getIdActionModuleGroup());
            jSONObject.put("text", actionmodulegroup.getName());
            jSONObject.put("name", actionmodulegroup.getName());
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
        Integer idActionModuleGroup = Integer.parseInt(((String[]) params.get("idActionModuleGroup"))[0].toString());
        Actionmodulegroup actionmodulegroup = this.actionmodulegroupService.findActionmodulegroupById(idActionModuleGroup);
        Set<Actionmodule> actionmodules=actionmodulegroup.getActionmodules();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Actionmodule actionmodule = new Actionmodule();
            actionmodule.setName(dataJSONObject.get("name").toString());
            actionmodule.setUrl(dataJSONObject.get("url").toString());
            actionmodule.setActionmodulegroup(actionmodulegroup);
            actionmodule.setRoleactionmodulemaps(null);
            if (dataJSONObject.get("idActionModule").toString().equals("null")) {
                this.actionmoduleService.addActionmodule(actionmodule);
                actionmodules.add(actionmodule);
            } else {
                actionmodule.setIdActionModule(Integer.parseInt(dataJSONObject.get("idActionModule").toString()));
                this.actionmoduleService.updateActionmodule(actionmodule);
            }
        }

        actionmodulegroup.setActionmodules(actionmodules);
        return Action.SUCCESS;
    }

    public String deleteDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idActionModuleGroup = Integer.parseInt(((String[]) params.get("idActionModuleGroup"))[0].toString());
        Actionmodulegroup actionmodulegroup = this.actionmodulegroupService.findActionmodulegroupById(idActionModuleGroup);
        Set<Actionmodule> actionmodules = actionmodulegroup.getActionmodules();

        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Actionmodule actionmodule = this.actionmoduleService.findActionmoduleById(Integer.parseInt(dataJSONObject.get("idActionModule").toString()));
            this.actionmoduleService.deleteActionmodule(actionmodule);
            actionmodules.remove(actionmodule);
        }

        actionmodulegroup.setActionmodules(actionmodules);
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
