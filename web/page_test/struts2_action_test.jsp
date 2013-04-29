<%-- 
    Document   : newjsp
    Created on : 2013-2-19, 23:33:38
    Author     : Administrator
--%>

<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.usermanager.model.User"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.cfg.Configuration"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
         <s:form action="/mystruts/sum.action" >                
              <s:textfield name="operand1" label=" 操作数1"/>
              <s:textfield name="operand2"  label=" 操作数2" />        
              <s:submit value="代数和" />            
          </s:form>
    </body>
</html>
