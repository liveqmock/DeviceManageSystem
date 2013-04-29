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
                var idActionModuleGroup=null;
                var idActionModule=null;
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
                        {name:"idActionModule",type:"int",mapping:"idActionModule"},
                        {name:"name",type:"string",mapping:"name"},
                        {name:"url",type:"string",mapping:"url"}
                    ]);
                    read_grid=new Ext.data.JsonReader({
                        root:'root',
                        totalProperty:'totalProperty',    
                        fields:TableRecord
                    });
                }
                
                function initHttpProxy(){
                    proxy_grid=new Ext.data.HttpProxy({
                        url:'/DeviceManageSystem/ActionmodulegroupmapManager/LoadDataToGridPanel.action',
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
                    cm=new Ext.grid.ColumnModel(
                    {
                        defaults: {
                            sortable: true
                        },
                        columns:[
                            rm,
                            sm,
                            {
                                header:"功能模块名称",
                                dataIndex:"name",
                                width:180,
                                editor:new Ext.form.TextField()
                            },{
                                header:"功能模块页面路径",
                                dataIndex:"url",
                                width:350,
                                editor:new Ext.form.TextArea()
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
                
                function initActionmoduleEditPanel(){
                    var actionmoduleEditPanel=new Ext.Panel({
                        border:false,
                        region: 'center',
                        layout:"fit",
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler : function(){
                                    var initValue={idActionModule:null,name:"新操作模块",url:null};
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
                                                            if(deleterecord.get('idActionModule')!=null){  
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
                                                    deleteData(json);
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
                                    updateData(modified); 
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
                    return actionmoduleEditPanel;
                }
                
                function initTreePanel(p){
                    var treePanel = new Ext.tree.TreePanel({
                        region: 'center',
                        border:false,
                        autoScroll: true,
                        lines:false, 
                        rootVisible: false,
                        loader: new Ext.tree.TreeLoader({dataUrl:'/DeviceManageSystem/ActionmodulegroupmapManager/LoadDataToTreePanel.action'}),
                        root:new Ext.tree.AsyncTreeNode({
                            draggable:false,
                            id:'888888888'
                        }),
                        listeners: {
                            click: function(n) {                        
                                idActionModuleGroup=n.attributes.id;
                                initColumnModel();
                                initJsonReader();
                                initHttpProxy();
                                initStore();  
                                store_grid.load({params:{idActionModuleGroup:idActionModuleGroup}});
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
                 
                function createActionmoduleManagerPanel(){
                    var actionmoduleEditPanel=initActionmoduleEditPanel();
                    var treePanel=initTreePanel(actionmoduleEditPanel);
                
                    var actionmoduleManagerPanel=new Ext.Panel({
                        closable:true,
                        width: document.body.clientWidth,
                        height:document.body.clientHeight,
                        layout:'border',//表格布局  
                        renderTo : "content", 
                        autoWidth : true,
                        items:[
                            {
                                xtype:"panel",
                                title:"功能模块类别",
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
                                items:[actionmoduleEditPanel]
                            }
                        ]
                    });       
                    return actionmoduleManagerPanel;
                }
  
                function deleteData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/ActionmodulegroupmapManager/DeleteDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idActionModuleGroup:idActionModuleGroup},  
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
                            url: "/DeviceManageSystem/ActionmodulegroupmapManager/UpdateDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idActionModuleGroup:idActionModuleGroup},  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() { store_grid.reload({params:{idActionModuleGroup:idActionModuleGroup}});});  
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
                
                var actionmoduleManagerPanel=createActionmoduleManagerPanel();
                
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


