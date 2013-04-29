<%-- 
    Document   : TablesFieldsManager
    Created on : 2013-2-17, 14:14:29
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
                var idRole=null;
                var TableRecord=null;
                var read_grid=null;
                var proxy_grid=null;
                var store_grid=null;
                var store1_1=null;
                var sm=null;
                var rm=null;
                var cm=null;
                var grid=null;   
                
                function initJsonReader(){
                    TableRecord=Ext.data.Record.create([
                        {name:"idRoleActionModuleMap",type:"int",mapping:"idRoleActionModuleMap"},
                        {name:"idRole",type:"int",mapping:"idRole"},
                        {name:"name",type:"string",mapping:"name"}
                    ]);
                    read_grid=new Ext.data.JsonReader({
                        root:'root',
                        totalProperty:'totalProperty',    
                        fields:TableRecord
                    });
                }
                
                function initHttpProxy(){
                    proxy_grid=new Ext.data.HttpProxy({
                        url:'/DeviceManageSystem/RoleactionmodulemapManager/LoadDataToGridPanel.action',
                        method : 'post' 
                    });
                }
                
                function initStore(){
                    store_grid=new Ext.data.Store({
                        autoLoad:true,  
                        reader:read_grid,
                        proxy:proxy_grid
                    });
                }
                
                function initColumnModel(){
                    sm = new Ext.grid.CheckboxSelectionModel();
                    rm=new Ext.grid.RowNumberer();
                    store1_1=new Ext.data.Store({  //井号选择下拉框  
                        proxy: new Ext.data.HttpProxy({     
                            url: '/DeviceManageSystem/RoleactionmodulemapManager/LoadActionmoduleNames.action',
                            method : 'post' 
                        }),     
                        reader: new Ext.data.JsonReader({     
                            root:'root',
                            totalProperty:'totalProperty' 
                        }, [     
                            {name:'name',mapping:'name'},     
                        ])     
                    });
                    
                    cm=new Ext.grid.ColumnModel(
                    {
                        defaults: {
                            sortable: true
                        },
                        columns:[
                            rm,
                            sm,
                            {
                                header:"功能模块",
                                dataIndex:"name",
                                width:300,
                                editor:new Ext.form.ComboBox({
                                    store:store1_1,
                                    displayField:'name',
                                    valueField:'name',
                                    listWidth:300,
                                    typeAhead: true,
                                    mode: 'remote',
                                    forceSelection: true,
                                    triggerAction: 'all',
                                    emptyText:'Select a state...',
                                    selectOnFocus:true
                                })
                            }
                        ]
                    });
                }
                          
                function initEditorGridPanel(){
                    grid = new Ext.grid.EditorGridPanel({
                        region: 'center',
                        border:false,
                        store:store_grid,
                        sm: sm,
                        cm: cm,
                        autoEncode:true 
                    });
                }
                
                function initCustomtableQueryActionManagerPanel(){
                    var tableFunctionMapEditPanel=new Ext.Panel({
                        border:false,
                        region: 'center',
                        layout:"fit",
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler : function(){
                                    var initValue={idRoleActionModuleMap:null,idRole:idRole,name:null};
                                    var tablerecord = new TableRecord(initValue);
                                    var view=grid.getView();
                                    grid.stopEditing();  
                                    var sm=grid.getSelectionModel(); 
                                    if(sm.hasSelection()){
                                        for(var i=view.getRows().length-1;i>=0;i--){
                                            if(sm.isSelected(i)){
                                                store_grid.insert(i,tablerecord);
                                            }
                                        }
                                    }else{
                                        store_grid.add(tablerecord);
                                    }
                                    tablerecord.dirty=true;
                                    tablerecord.modified=initValue;
                                    if(store_grid.modified.indexOf(tablerecord)==-1){
                                        store_grid.modified.push(tablerecord);
                                    }
                                    view.refresh();
                                }
                            },
                            {
                                xtype:"button",
                                text:"删除",
                                iconCls:"delete",
                                handler : function(){
                                    var sm=grid.getSelectionModel();
                                    var view=grid.getView();
                                    if(sm.hasSelection()){
                                        Ext.Msg.confirm("提示","是否删除选中行数据",function(btn)
                                        {              
                                            if(btn=="yes"){
                                                var json=[];
                                                for(var i=view.getRows().length-1;i>=0;i--){
                                                    if(sm.isSelected(i)){
                                                        var deleterecord=store_grid.getAt(i);
                                                        grid.stopEditing();  
                                                        if(store_grid.modified.indexOf(deleterecord)==-1){
                                                            //Ext.Msg.alert("提示","执行数据库删除操作");
                                                            json.push(deleterecord);
                                                            store_grid.remove(deleterecord)
                                                        }else{
                                                            if(deleterecord.get('idRoleActionModuleMap')!=null){  
                                                                //Ext.Msg.alert("提示","原始数据项，执行数据库删除操作 "+"ID:"+deleterecord.get('IDCUSTOMTABLE'));
                                                                json.push(deleterecord);
                                                                store_grid.remove(deleterecord);
                                                                store_grid.modified.remove(deleterecord);
                                                            }else{
                                                                //Ext.Msg.alert("提示","非原始数据项，不执行数据库删除操作");
                                                                store_grid.remove(deleterecord);
                                                                store_grid.modified.remove(deleterecord);
                                                            }                  
                                                        }
                                                    }
                                                }
                                                if(json.length>0){
                                                    deleteData_QueryActionMap(json);
                                                }
                                            }
                                            view.refresh();
                                        });
                                    }else{
                                        Ext.Msg.alert("警告","请先选中要删除的行");
                                    }
                                }
                            },
                            {
                                xtype:"button",
                                text:"提交",
                                iconCls:"submit",
                                handler: function() {  
                                    var modified = store_grid.modified;  
                                    updateData_QueryActionMap(modified); 
                                    store_grid.modified=[]
                                } 
                            },
                            '-',
                            {
                                xtype:"button",
                                text:"刷新",
                                iconCls:"refresh",
                                handler:function(){
                                    window.location.reload();
                                }       
                            }
                        ]
                    }); 
                    return tableFunctionMapEditPanel;
                }
                
                function initTreePanel(p){
                    var treePanel = new Ext.tree.TreePanel({
                        region: 'center',
                        border:false,
                        autoScroll: true,
                        lines:false, 
                        rootVisible: false,
                        loader: new Ext.tree.TreeLoader({dataUrl:'/DeviceManageSystem/RoleactionmodulemapManager/LoadDataToTreePanel.action'}),
                        root:new Ext.tree.AsyncTreeNode({
                            draggable:false,
                            id:'888888888'
                        }),
                        listeners: {
                            click: function(n) {                        
                                idRole=n.attributes.id;
                                initColumnModel();
                                initJsonReader();
                                initHttpProxy();
                                initStore();  
                                store_grid.load({params:{idRole:idRole}});
                                initEditorGridPanel();
                                p.removeAll();
                                p.add(grid);
                                p.doLayout();
                                grid.getView().refresh();
                            }
                        }
                    });
                    return treePanel;
                }
                 
                function createcustomtableQueryActionManagerPanel(){
                    var tableFunctionMapEditPanel=initCustomtableQueryActionManagerPanel();
                    var treePanel=initTreePanel(tableFunctionMapEditPanel);
                
                    var tableFunctionMapManagerPanel=new Ext.Panel({
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
                                items:[treePanel]
                            },
                            {
                                xtype:"panel",
                                region:"center",
                                layout:"border",
                                border:false,
                                items:[tableFunctionMapEditPanel]
                            }
                        ]
                    });       
                    return tableFunctionMapManagerPanel;
                }
  
                function deleteData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/RoleactionmodulemapManager/DeleteDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idRole:idRole},  
                            method: "POST",  
                            success: function(response) {},  
                            failure: function(response) {Ext.Msg.alert("警告", "删除数据错误！");}  
                        });  
                    }  
                    else {  
                        Ext.Msg.alert("警告", "没有任何需要删除的数据！");  
                    }  
                } 
                
                function updateData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/RoleactionmodulemapManager/UpdateDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idRole:idRole},  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() { store_grid.reload({params:{idRole:idRole}});});  
                            },  
                            failure: function(response) {  
                                Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");  
                            }  
                        });  
                    }  
                    else {  
                        Ext.Msg.alert("警告", "没有任何需要更新的数据！");  
                    }  
                }         
                
                var tableFunctionMapManagerPanel=createcustomtableQueryActionManagerPanel();
                
                Ext.EventManager.onWindowResize(function(){ 
                    tableFunctionMapManagerPanel.setWidth(document.body.clientWidth);
                    tableFunctionMapManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>


