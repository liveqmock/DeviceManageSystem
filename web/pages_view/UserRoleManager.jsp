<%-- 
    Document   : UserManager
    Created on : 2013-2-17, 14:15:11
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
                var tree=new Ext.tree.TreePanel({
                    region: 'center',
                    autoScroll: true,
                    border:false,
                    loader: new Ext.tree.TreeLoader(),
                    root: new Ext.tree.AsyncTreeNode({
                        expanded: true,
                        children: [{
                                text: '信息中心',
                                leaf: true
                            }, {
                                text: '系统运行部',
                                leaf: true
                            },{
                                text: '变电管理所',
                                leaf: false,
                                children:[{                      
                                        text: '金城江巡维中心',
                                        leaf: true          
                                    }]
                            }]
                    }),
                    rootVisible: false,
                    listeners: {
                        click: function(n) {
                            Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
                        }
                    }
                });
    
                var sm = new Ext.grid.CheckboxSelectionModel(); 
                var cm=new Ext.grid.ColumnModel({
                    defaults: {
                        sortable: true
                    },
                    columns: [        
                        new Ext.grid.RowNumberer(),
                        sm,
                        {
                            header:"用户名",
                            dataIndex:"name",
                            width:130,
                            editor:new Ext.form.TextField()
                        },{
                            header:"用户姓名",
                            dataIndex:"name",
                            width:130,
                            editor:new Ext.form.TextField()
                        },
                        {
                            header:"部门",
                            dataIndex:"comment",
                            width:130,
                            editor:new Ext.form.TextArea ()
                        },
                        {
                            header:"岗位",
                            dataIndex:"comment",
                            width:130,
                            editor:new Ext.form.TextArea ()
                        }
                    ]
                })  
    
                var grid = new Ext.grid.GridPanel({
                    region: 'center',
                    border:false,
                    store: new Ext.data.Store(),
                    cm: cm,
                    sm: sm
                });
    
                var userManagerPanel=new Ext.Panel({
                    closable:true,
                    width: document.body.clientWidth,
                    height:document.body.clientHeight,
                    layout:'border',//表格布局  
                    renderTo : "content", 
                    autoWidth : true,
                    items:[
                        {
                            xtype:"panel",
                            title:"角色",
                            region:"west",
                            collapsible:true,
                            layout:"border",
                            border:false,
                            width:210,
                            split:true,
                            items:[tree]
                        },
                        {
                            xtype:"panel",
                            region:"center",
                            layout:"border",
                            border:false,
                            tbar:[
                                {
                                    xtype:"button",
                                    text:"新增",
                                    iconCls:"add"
                                },
                                {
                                    xtype:"button",
                                    text:"删除",
                                    iconCls:"delete"
                                },{
                                    xtype:"button",
                                    text:"提交",
                                    iconCls:"submit"
                                },
                                '-',
                                {
                                    xtype:"button",
                                    text:"刷新",
                                    iconCls:"refresh"
                                }
                            ],
                            items:[grid]
                        }
                    ]
                });
                Ext.EventManager.onWindowResize(function(){ 
                    userManagerPanel.setWidth(document.body.clientWidth);
                    userManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>
