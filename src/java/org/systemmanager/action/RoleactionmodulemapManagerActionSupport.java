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
import org.systemmanager.model.Role;
import org.systemmanager.model.Roleactionmodulemap;
import org.systemmanager.service.ActionmoduleService;
import org.systemmanager.service.RoleService;
import org.systemmanager.service.RoleactionmodulemapService;

/**
 *
 * @author Administrator
 */
public class RoleactionmodulemapManagerActionSupport extends ActionSupport {
    
    private RoleactionmodulemapService roleactionmodulemapService;
    private RoleService roleService;
    private ActionmoduleService actionmoduleService;
    private String totalProperty;
    private List root;
    
    public RoleactionmodulemapManagerActionSupport() {
        this.roleactionmodulemapService=new RoleactionmodulemapService();
        this.roleService=new RoleService();
        this.actionmoduleService=new ActionmoduleService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        Integer idRole = Integer.parseInt(((String[]) params.get("idRole"))[0].toString());
        Role role=this.roleService.findRoleById(idRole);
        List<Roleactionmodulemap> roleactionmodulemaps = this.roleactionmodulemapService.findRoleactionmodulemapByRole(role);
        this.setRoot(new ArrayList<Map>());
        
        for (Roleactionmodulemap roleactionmodulemap : roleactionmodulemaps) {
            Map dataMap = new HashMap();
            dataMap.put("idRoleActionModuleMap", roleactionmodulemap.getIdRoleActionModuleMap());
            dataMap.put("idRole", idRole);
            dataMap.put("name", roleactionmodulemap.getActionmodule().getName());
            this.getRoot().add(dataMap);
        }
        return Action.SUCCESS;
    }
    
    public void loadDataToTreePanel() throws IOException {
        List<Role> roles = this.roleService.getAllRole();
        String jsonString = null;
        for (Role role : roles) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", role.getIdRole());
            jSONObject.put("text", role.getName());
            jSONObject.put("name", role.getName());
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
        Integer idRole = Integer.parseInt(((String[]) params.get("idRole"))[0].toString());
        Role role = this.roleService.findRoleById(idRole);
        Set<Roleactionmodulemap> roleactionmodulemaps=role.getRoleactionmodulemaps();
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Roleactionmodulemap roleactionmodulemap = new Roleactionmodulemap();
            roleactionmodulemap.setRole(this.roleService.findRoleById(Integer.parseInt(dataJSONObject.get("idRole").toString())));
            Actionmodule actionmodule=(Actionmodule) (this.actionmoduleService.findActionmoduleByName(dataJSONObject.get("name").toString())).toArray()[0];
            roleactionmodulemap.setActionmodule(actionmodule);
            roleactionmodulemap.setDate("");
            if (dataJSONObject.get("idRoleActionModuleMap").toString().equals("null")) {    
                this.roleactionmodulemapService.addRoleactionmodulemap(roleactionmodulemap);
                roleactionmodulemaps.add(roleactionmodulemap);
            } else {
                roleactionmodulemap.setIdRoleActionModuleMap(Integer.parseInt(dataJSONObject.get("idRoleActionModuleMap").toString()));
                this.roleactionmodulemapService.updateRoleactionmodulemap(roleactionmodulemap);
            }
        }
        
        role.setRoleactionmodulemaps(roleactionmodulemaps);
        return Action.SUCCESS;
    }
    
    public String deleteDataToDb() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        String jsonString = "{root:" + ((String[]) params.get("data"))[0].toString() + "}";
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键     
        Integer idRole = Integer.parseInt(((String[]) params.get("idRole"))[0].toString());
        Role role = this.roleService.findRoleById(idRole);
        Set<Roleactionmodulemap> roleactionmodulemaps=role.getRoleactionmodulemaps();
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);  
            Roleactionmodulemap roleactionmodulemap=this.roleactionmodulemapService.findRoleactionmodulemapById(Integer.parseInt(dataJSONObject.get("idRoleActionModuleMap").toString()));
            this.roleactionmodulemapService.deleteRoleactionmodulemap(roleactionmodulemap);
            roleactionmodulemaps.remove(roleactionmodulemap);
        }
        
        role.setRoleactionmodulemaps(roleactionmodulemaps);
        return Action.SUCCESS;
    }
    
    public String loadActionmoduleNames() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        this.root = new ArrayList<Map>();

        List<Actionmodule> actionmodules = this.actionmoduleService.getAllActionmodule();

        for (Actionmodule actionmodule : actionmodules) {
            Map dataMap = new HashMap();
            dataMap.put("name", actionmodule.getName());
            this.root.add(dataMap);
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
