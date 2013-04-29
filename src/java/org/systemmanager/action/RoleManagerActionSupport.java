/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.systemmanager.action;

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
import org.dbmanager.service.CustomtablecolumnService;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.systemmanager.model.Role;
import org.systemmanager.model.Roleactionmodulemap;
import org.systemmanager.model.Userrolemap;
import org.systemmanager.service.RoleService;
import org.systemmanager.service.RoleactionmodulemapService;
import org.systemmanager.service.UserrolemapService;

/**
 *
 * @author Administrator
 */
public class RoleManagerActionSupport extends ActionSupport {
    
    private RoleService roleService;
    private String totalProperty;
    private List root;
    
    public RoleManagerActionSupport() {
        this.roleService=new RoleService();
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String loadDataToGridPanel() {
        List<Role> roles = roleService.getAllRole();
        this.setRoot(new ArrayList<Map>());
        for (Role role : roles) {
            Map dataMap = new HashMap();
            dataMap.put("idRole", role.getIdRole());
            dataMap.put("name", role.getName());
            dataMap.put("comment", role.getComment());
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
            Role role = new Role();
            role.setName(dataJSONObject.get("name").toString());
            role.setComment(dataJSONObject.get("comment").toString());
            if (dataJSONObject.get("idRole").toString().equals("null")) {
                roleService.addRole(role);
            } else {
                role.setIdRole(Integer.parseInt(dataJSONObject.get("idRole").toString()));
                roleService.updateRole(role);
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
        RoleactionmodulemapService roleactionmodulemapService = new RoleactionmodulemapService();
        UserrolemapService userrolemapService=new UserrolemapService();
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Role role = roleService.findRoleById(Integer.parseInt(dataJSONObject.get("idRole").toString()));
            Set<Roleactionmodulemap> roleactionmodulemaps = role.getRoleactionmodulemaps();
            Set<Userrolemap> userrolemaps=role.getUserrolemaps();
            
            if (roleactionmodulemaps.size() == 0) {
                System.out.println("size is 0");
            } else {
                for (Roleactionmodulemap roleactionmodulemap : roleactionmodulemaps) {
                    roleactionmodulemapService.deleteRoleactionmodulemap(roleactionmodulemap);
                }
            }
            
            if(userrolemaps.size()==0){
                System.out.println("size is 0");
            }else{
                for (Userrolemap userrolemap : userrolemaps) {
                    userrolemapService.deleteUserrolemap(userrolemap);
                }
            }
            
            roleService.deleteRole(role);
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
