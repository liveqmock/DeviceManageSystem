/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devicemanager.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
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
public class DeviceFreeQueryActionSupport extends ActionSupport {

    private CustomtableService customtableService;
    private CustomtablecolumnService customtablecolumnService;
    public static Integer idcustomtable;
    private ArrayList<Map> root;
    public static ArrayList<Map> resultMaps;
    private int totalProperty;

    public DeviceFreeQueryActionSupport() {
        this.customtableService=new CustomtableService();
        this.customtablecolumnService=new CustomtablecolumnService();
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadDataToTreePanel() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();

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

    public void loadGridColumn() throws IOException {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        String json = null;
        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            if (!customtablecolumn.isColumnIsHide()) {
                if (json == null) {
                    json = "{header:\"" + customtablecolumn.getColumnShowName() + "\",dataIndex:\"" + customtablecolumn.getColumnName() + "\",sortable:true,width:130,editor:new Ext.grid.GridEditor(new Ext.form." + customtablecolumn.getColumnControlType() + "(" + customtablecolumn.getColumnControlDefine() + "))}";
                } else {
                    json = json + ",\n{header:\"" + customtablecolumn.getColumnShowName() + "\",dataIndex:\"" + customtablecolumn.getColumnName() + "\",sortable:true,width:130,editor:new Ext.grid.GridEditor(new Ext.form." + customtablecolumn.getColumnControlType() + "(" + customtablecolumn.getColumnControlDefine() + "))}";
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
        idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());

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
        idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        int start = Integer.parseInt(((String[]) params.get("start"))[0].toString());
        int limit = Integer.parseInt(((String[]) params.get("limit"))[0].toString());
        String columnName = ((String[]) params.get("columnName"))[0].toString();
        String searchValue = ((String[]) params.get("searchValue"))[0].toString();
        String dataString = ((String[]) params.get("data"))[0].toString();

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);
        this.root = new ArrayList<Map>();

        String queryString = "select * from devicemanagerdb." + customtable.getName();

        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();
        
        if (columnName==null||columnName=="") {
            
        }else{
            String queryCaseString = columnName + " like " + "'%" + searchValue + "%'";
            queryString = queryString + " where " + queryCaseString;
        }

        if (dataString == null || dataString == "") {
            System.out.println("no search data");
        } else {
            String jsonString = "{root:" + dataString + "}";
            JSONObject jSONObject = JSONObject.fromObject(jsonString);
            JSONArray dataRecords = jSONObject.getJSONArray("root");//json数组对应的键   
            String queryCaseString = null;

            for (Object dataRocord : dataRecords) {
                JSONObject dataJSONObject = JSONObject.fromObject(dataRocord);
                if (queryCaseString == null) {
                    if (dataJSONObject.get("relation").equals("END")) {
                        if (dataJSONObject.get("queryCase").equals("IS NOT NULL") || dataJSONObject.get("queryCase").equals("IS NULL")) {
                            queryCaseString = dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase");
                        } else {
                            queryCaseString = dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " '" + dataJSONObject.get("value") + "'";
                        }
                    } else {
                        if (dataJSONObject.get("queryCase").equals("IS NOT NULL") || dataJSONObject.get("queryCase").equals("IS NULL")) {
                            queryCaseString = dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " " + dataJSONObject.get("relation");
                        } else {
                            queryCaseString = dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " '" + dataJSONObject.get("value") + "' " + dataJSONObject.get("relation");
                        }
                    }
                } else {
                    if (dataJSONObject.get("relation").equals("END")) {
                        if (dataJSONObject.get("queryCase").equals("IS NOT NULL") || dataJSONObject.get("queryCase").equals("IS NULL")) {
                            queryCaseString = queryCaseString + " " + dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase");
                        } else {
                            queryCaseString = queryCaseString + " " + dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " '" + dataJSONObject.get("value") + "'";
                        }
                    } else {
                        if (dataJSONObject.get("queryCase").equals("IS NOT NULL") || dataJSONObject.get("queryCase").equals("IS NULL")) {
                            queryCaseString = queryCaseString + " " + dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " " + dataJSONObject.get("relation");
                        } else {
                            queryCaseString = queryCaseString + " " + dataJSONObject.get("columnName") + " " + dataJSONObject.get("queryCase") + " '" + dataJSONObject.get("value") + "' " + dataJSONObject.get("relation");
                        }
                    }
                }
            }

            queryString = queryString + " where " + queryCaseString;
        }

        System.out.print(queryString);

        ResultSet resultSet = statement.executeQuery(queryString);
        int count = 0;
        this.resultMaps = new ArrayList<Map>();
        while (resultSet.next() && count < start + limit) {
            if (count < start) {
                count++;
            } else {
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
                this.root.add(dataMap);
                count++;
            }
        }

        resultSet.beforeFirst();
        while (resultSet.next()) {
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
            this.resultMaps.add(dataMap);
        }

        resultSet.last();
        this.setTotalProperty(resultSet.getRow());
        statement.close();
        connection.close();

        return Action.SUCCESS;
    }

    public String loadCustomtablecolumnNames() {
        ActionContext context = ActionContext.getContext();
        Map params = context.getParameters();
        idcustomtable = Integer.parseInt(((String[]) params.get("idCustomTable"))[0].toString());
        this.root = new ArrayList<Map>();

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        for (Customtablecolumn customtablecolumn : customtablecolumns) {
            Map dataMap = new HashMap();
            dataMap.put("columnName", customtablecolumn.getColumnName());
            dataMap.put("columnShowName", customtablecolumn.getColumnShowName());
            this.root.add(dataMap);
        }

        return Action.SUCCESS;
    }

    public String createExcel() throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab
        HSSFSheet sheet = workbook.createSheet("sheet1");
        // 创建字体样式
        HSSFFont font = workbook.createFont();
        font.setFontName("Verdana");
        font.setBoldweight((short) 100);
        font.setFontHeight((short) 300);
        font.setColor(HSSFColor.BLUE.index);
        // 创建单元格样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置边框
        style.setBottomBorderColor(HSSFColor.RED.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置字体
        style.setFont(font);
        // 创建Excel的sheet的一行
        HSSFRow row;
        HSSFCell cell;
        int cellNum = 0;
        int rowNum = 0;
        ArrayList<Map> results = this.resultMaps;

        Customtable customtable = customtableService.findCustomtableById(idcustomtable);
        List<Customtablecolumn> customtablecolumns = customtablecolumnService.findCustomtablecolumnByCustomtable(customtable);

        if (rowNum == 0) {
            row = sheet.createRow(rowNum);
            for (Customtablecolumn customtablecolumn : customtablecolumns) {
                cell = row.createCell(cellNum);
                cell.setCellValue(customtablecolumn.getColumnShowName());
                cellNum++;
            }
            cellNum = 0;
            rowNum++;
        }
        if (rowNum != 0) {
            for (Map resultMap : results) {
                row = sheet.createRow(rowNum);
                for (Customtablecolumn customtablecolumn : customtablecolumns) {
                    cell = row.createCell(cellNum);
                    if(resultMap.get(customtablecolumn.getColumnName())==null||resultMap.get(customtablecolumn.getColumnName())==""){
                        cell.setCellValue("");
                    }else{
                        cell.setCellValue(resultMap.get(customtablecolumn.getColumnName()).toString());
                    }              
                    cellNum++;
                }
                cellNum = 0;
                rowNum++;
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        byte[] ba = outputStream.toByteArray();
        ByteArrayInputStream br = new ByteArrayInputStream(ba);

        byte[] buf = new byte[1024];
        int len = 0;

        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/xls;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=Result.xls");
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.flush();
        out.close();

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
