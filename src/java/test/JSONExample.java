/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dbmanager.model.Customtable;
import org.dbmanager.service.CustomtableService;

/**
 *
 * @author Administrator
 */
public class JSONExample extends ActionSupport {

    private CustomtableService customtableService;
    private String tobalProperty;
    private List root;

    public String execute() {
        return null;
    }

    public String loadDataToGridPanel() {
        this.customtableService = new CustomtableService();
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
        this.customtableService = new CustomtableService();
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtable customtable = new Customtable();
            customtable.setName(dataJSONObject.get("NAME").toString());
            customtable.setShowName(dataJSONObject.get("SHOWNAME").toString());
            customtable.setType(Integer.parseInt(dataJSONObject.get("TYPE").toString()));
            customtable.setComment(dataJSONObject.get("COMMENT").toString());
            if (dataJSONObject.get("IDCUSTOMTABLE").toString().equals("null")) {
                customtableService.addCustomtable(customtable);
            } else {
                customtable.setIdCustomTable(Integer.parseInt(dataJSONObject.get("IDCUSTOMTABLE").toString()));
                customtableService.updateCustomtable(customtable);
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
        this.customtableService = new CustomtableService();
        
        for (Object object : dataRecords) {
            JSONObject dataJSONObject = JSONObject.fromObject(object);
            Customtable customtable = new Customtable();
            customtable.setIdCustomTable(Integer.parseInt(dataJSONObject.get("IDCUSTOMTABLE").toString()));
            customtable.setName(dataJSONObject.get("NAME").toString());
            customtable.setShowName(dataJSONObject.get("SHOWNAME").toString());
            customtable.setType(Integer.parseInt(dataJSONObject.get("TYPE").toString()));
            customtable.setComment(dataJSONObject.get("COMMENT").toString());
            customtableService.deleteCustomtable(customtable);
        }
        return Action.SUCCESS;
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
