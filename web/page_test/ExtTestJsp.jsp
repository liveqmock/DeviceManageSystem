<%-- 
    Document   : SQLManager
    Created on : 2013-2-17, 14:13:47
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
            Ext.onReady(function(){      
                var TableRecord=null;
                var read_grid=null;
                var proxy_grid=null;
                var store_grid=null;
                var sm=null;
                var rm=null;
                var cm=null;
                var grid=null;
                var sqlManagerPanel=null;
                
                function initJsonReader_QueryActionMap(){
                    Ext.Ajax.request(
                    {
                        url:'/project1/Test/LoadGridRecord.action?idcustomtable='+idCustomTable,
                        success:function(response,option){
                            var json=response.responseText;
                            json = "[" + json + "]";
                            TableRecord_QueryActionMap=Ext.data.Record.create(eval(json));
                            read_grid=new Ext.data.JsonReader({
                                root:'root',
                                tobalProperty:'tobalProperty',    
                                fields:TableRecord
                            });
                        },
                        failure:function(){Ext.Msg.alert("消息","出错aaa");}
                    })
                }
                
                function initHttpProxy_QueryActionMap(){
                    proxy_grid=new Ext.data.HttpProxy({
                        url:'/project1/CustomfunctionManager/LoadDataToGridPanel.action',
                        method : 'post' 
                    });
                }
                
                function initColumnModel_QueryActionMap(){
                    sm = new Ext.grid.CheckboxSelectionModel();
                    rm=new Ext.grid.RowNumberer();
                    Ext.Ajax.request(
                    {
                        url:'/project1/Test/LoadGridColumn.action?idcustomtable='+idCustomTable,
                        success:function(response,option){
                            var json=response.responseText;
                            json = "[rm,sm," + json + "]";
                            cm=new Ext.grid.ColumnModel(eval(json));
                        },
                        failure:function(){Ext.Msg.alert("消息","出错bbb");}
                    });
                }
                 
                function initStore_QueryActionMap(){
                    store_grid=new Ext.data.Store({
                        autoLoad:true,  
                        reader:read_grid,
                        proxy:proxy_grid
                    });
                }
            
                function initEditorGridPanel_QueryActionMap(){
                    grid = new Ext.grid.EditorGridPanel({
                        region: 'center',
                        border:false,
                        store:store_grid,
                        sm: sm,
                        cm: cm,
                        autoEncode:true,
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler : function(){
                                    Ext.Ajax.request({
                                        url:'/project1/Test/LoadGridColumn.action',
                                        success:function(response,option){
                                            var res = Ext.util.JSON.decode(response.responseText);
                                            Ext.MessageBox.alert("提示","header:"+res.name);
                                        },
                                        failure:function(){Ext.Msg.alert("消息","查询出错---->请打开数据库查看数据表名字是否正确");}
                                    });
                                }
                            },
                            {
                                xtype:"button",
                                text:"删除",
                                iconCls:"delete"
                            },
                            {
                                xtype:"button",
                                text:"提交",
                                iconCls:"submit"
                            },
                            {
                                xtype:"button",
                                text:"刷新",
                                iconCls:"refresh"
                            }
                        ]
                    }); 
                }
                
                function initPanel(){
                    sqlManagerPanel=new Ext.Panel({
                        closable:true,
                        width: document.body.clientWidth,
                        height:document.body.clientHeight,
                        layout:'border',//表格布局  
                        renderTo : "content", 
                        autoWidth : true,
                        items: [{
                                xtype:"panel",
                                region: 'center',
                                layout: 'border',
                                border:false,
                                items:[grid]
                            }]
                    });
                }
                
                initColumnModel_QueryActionMap();
                initJsonReader_QueryActionMap();
                initHttpProxy_QueryActionMap();
                setTimeout(function(){
                    initStore_QueryActionMap();  
                    store_grid.load();
                    initEditorGridPanel_QueryActionMap();
                    initPanel();},
                800);
     
                Ext.EventManager.onWindowResize(function(){ 
                    sqlManagerPanel.setWidth(document.body.clientWidth);
                    sqlManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content"></div>
    </body>
