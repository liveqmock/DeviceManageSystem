/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

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
import org.apache.struts2.ServletActionContext;
import org.dbmanager.model.Customtable;
import org.dbmanager.model.Customtablecolumn;
import org.dbmanager.service.CustomtableService;
import org.dbmanager.service.CustomtablecolumnService;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 *
 * @author mac
 */
public class ExtTestActionSupport extends ActionSupport {

    private CustomtableService customtableService;
    private CustomtablecolumnService customtablecolumnService;
    private ArrayList<Map> root;

    public ExtTestActionSupport() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
