<%-- 
    Document   : newjsp1
    Created on : 2013-2-28, 14:01:32
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <s:form action="/JSONTest/JSONTest.action" >                     
              <s:submit value="代数和" />            
          </s:form>
    </body>
</html>
