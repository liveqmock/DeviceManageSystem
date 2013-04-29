/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dbmanager.model.Customtable;
import org.dbmanager.service.CustomtableService;
import org.dao.util.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.DatasourceConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 *
 * @author Administrator
 */
public class NewClass {

    public static void main(String args[]) throws SQLException {
        SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) new Configuration().configure().buildSessionFactory();
        Connection connection = sessionFactory.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();
        int b = statement.executeUpdate("INSERT INTO devicemanagerdb.department2 (idDepartment,name) VALUES (null,'asd')");
        connection.commit();
        System.out.print(b);
    }
}
