<%-- 
    Document   : DevicesQuery
    Created on : 2013-2-17, 14:12:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title title="hellow world"></title>
        <link rel="stylesheet" type="text/css" href="../ext3/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="../ext3/resources/css/ext-extend.css" />
        <script type="text/javascript" src="../ext3/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="../ext3/ext-all.js"></script>
        <script type="text/javascript" src="../ext3/resources/charts.swf"></script>
        <script type="text/javascript" src="../ext3/ext-lang-zh_CN.js"></script>
        <style type="text/css">    
            html,body{        
                margin:0px;    
                height:100%;   
                overflow-x:hidden;
                overflow-y:hidden; 
            }    
            #content{    
                height:100%;    
                width:100%;    
            }    
        </style>
        <script type="text/javascript">
            Ext.chart.Chart.CHART_URL = '../ext3/resources/charts.swf';
            Ext.onReady(function(){
                var store = new Ext.data.JsonStore({
                    fields: ['season', 'total'],
                    data: [{
                            season: 'Summer',
                            total: 150
                        },{
                            season: 'Fall',
                            total: 245
                        },{
                            season: 'Winter',
                            total: 117
                        },{
                            season: 'Spring',
                            total: 184
                        }]
                });
    
                new Ext.Panel({
                    width: document.body.clientWidth,
                    height:document.body.clientHeight,
                    renderTo: 'content',
                    items: {
                        store: store,
                        xtype: 'piechart',
                        dataField: 'total',
                        categoryField: 'season',
                        //extra styles get applied to the chart defaults
                        extraStyle:
                            {
                            legend:
                                {
                                display: 'bottom',
                                padding: 5,
                                font:
                                    {
                                    family: 'Tahoma',
                                    size: 13
                                }
                            }
                        }
                    }
                });
                Ext.EventManager.onWindowResize(function(){ 
                    sqlManagerPanel.setWidth(document.body.clientWidth);
                    sqlManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    <body>
        <div id="content">
        </div>
    </body>
</html>
