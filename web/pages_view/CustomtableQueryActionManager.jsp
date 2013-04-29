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
                var idCustomTable;
                var idCustomQueryAction;
                var TableRecord_QueryActionMap=null;
                var read_grid_QueryActionMap=null;
                var proxy_grid_QueryActionMap=null;
                var store_grid_QueryActionMap=null;
                var sm_QueryActionMap=null;
                var rm_QueryActionMap=null;
                var cm_QueryActionMap=null;
                var grid_QueryActionMap=null;   
                var TableRecord_QueryActionEdit=null;
                var read_grid_QueryActionEdit=null;
                var proxy_grid_QueryActionEdit=null;
                var store_grid_QueryActionEdit=null;
                var store1_1=null;
                var sm_QueryActionEdit=null;
                var rm_QueryActionEdit=null;
                var cm_QueryActionEdit=null;
                var grid_QueryActionEdit=null;  
                var treePanel_QueryActionMap;
                var treePanel_QueryActionEdit;
                var customtableQueryActionEditPanel;
                
                function initJsonReader_QueryActionMap(){
                    TableRecord_QueryActionMap=Ext.data.Record.create([
                        {name:"idCustomTableQueryActionMap",type:"int",mapping:"idCustomTableQueryActionMap"},
                        {name:"idCustomTable",type:"int",mapping:"idCustomTable"},
                        {name:"idCustomQueryAction",type:"int",mapping:"idCustomQueryAction"},
                        {name:"name",type:"string",mapping:"name"},
                        {name:"comment",type:"string",mapping:"comment"}
                    ]);
                    read_grid_QueryActionMap=new Ext.data.JsonReader({
                        root:'root',
                        totalProperty:'totalProperty',    
                        fields:TableRecord_QueryActionMap
                    });
                }
                
                function initHttpProxy_QueryActionMap(){
                    proxy_grid_QueryActionMap=new Ext.data.HttpProxy({
                        url:'/DeviceManageSystem/CustomtablequeryactionmapManager/LoadDataToGridPanel.action',
                        method : 'post' 
                    });
                }
                
                function initStore_QueryActionMap(){
                    store_grid_QueryActionMap=new Ext.data.Store({
                        autoLoad:true,  
                        reader:read_grid_QueryActionMap,
                        proxy:proxy_grid_QueryActionMap
                    });
                }
                
                function initColumnModel_QueryActionMap(){
                    sm_QueryActionMap = new Ext.grid.CheckboxSelectionModel();
                    rm_QueryActionMap=new Ext.grid.RowNumberer();              
                    cm_QueryActionMap=new Ext.grid.ColumnModel(
                    {
                        defaults: {
                            sortable: true
                        },
                        columns:[
                            rm_QueryActionMap,
                            sm_QueryActionMap,
                            {
                                header:"查询操作名称",
                                dataIndex:"name",
                                width:180,
                                editor:new Ext.form.TextField()
                            },
                            {
                                header:"备注",
                                dataIndex:"comment",
                                width:350,
                                editor:new Ext.form.TextArea()
                            }
                        ]
                    });
                }
                          
                function initEditorGridPanel_QueryActionMap(){
                    grid_QueryActionMap = new Ext.grid.EditorGridPanel({
                        region: 'center',
                        border:false,
                        store:store_grid_QueryActionMap,
                        sm: sm_QueryActionMap,
                        cm: cm_QueryActionMap,
                        autoEncode:true 
                    });
                }
                
                function initCustomtableQueryActionManagerPanel(){
                    var customtableQueryActionManagerPanel=new Ext.Panel({
                        border:false,
                        region: 'center',
                        layout:"fit",
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler : function(){
                                    var initValue={idCustomTableQueryActionMap:null,idCustomTable:idCustomTable,idCustomQueryAction:null,name:"新查询操作",comment:"备注"};
                                    var tablerecord = new TableRecord_QueryActionMap(initValue);
                                    var view=grid_QueryActionMap.getView();
                                    grid_QueryActionMap.stopEditing();  
                                    var sm_QueryActionMap=grid_QueryActionMap.getSelectionModel(); 
                                    if(sm_QueryActionMap.hasSelection()){
                                        for(var i=view.getRows().length-1;i>=0;i--){
                                            if(sm_QueryActionMap.isSelected(i)){
                                                store_grid_QueryActionMap.insert(i,tablerecord);
                                            }
                                        }
                                    }else{
                                        store_grid_QueryActionMap.add(tablerecord);
                                    }
                                    tablerecord.dirty=true;
                                    tablerecord.modified=initValue;
                                    if(store_grid_QueryActionMap.modified.indexOf(tablerecord)==-1){
                                        store_grid_QueryActionMap.modified.push(tablerecord);
                                    }
                                    view.refresh();
                                }
                            },
                            {
                                xtype:"button",
                                text:"删除",
                                iconCls:"delete",
                                handler : function(){
                                    var sm_QueryActionMap=grid_QueryActionMap.getSelectionModel();
                                    var view=grid_QueryActionMap.getView();
                                    if(sm_QueryActionMap.hasSelection()){
                                        Ext.Msg.confirm("提示","是否删除选中行数据",function(btn)
                                        {              
                                            if(btn=="yes"){
                                                var json=[];
                                                for(var i=view.getRows().length-1;i>=0;i--){
                                                    if(sm_QueryActionMap.isSelected(i)){
                                                        var deleterecord=store_grid_QueryActionMap.getAt(i);
                                                        grid_QueryActionMap.stopEditing();  
                                                        if(store_grid_QueryActionMap.modified.indexOf(deleterecord)==-1){
                                                            //Ext.Msg.alert("提示","执行数据库删除操作");
                                                            json.push(deleterecord);
                                                            store_grid_QueryActionMap.remove(deleterecord)
                                                        }else{
                                                            if(deleterecord.get('idCustomQueryAction')!=null){  
                                                                //Ext.Msg.alert("提示","原始数据项，执行数据库删除操作 "+"ID:"+deleterecord.get('IDCUSTOMTABLE'));
                                                                json.push(deleterecord);
                                                                store_grid_QueryActionMap.remove(deleterecord);
                                                                store_grid_QueryActionMap.modified.remove(deleterecord);
                                                            }else{
                                                                //Ext.Msg.alert("提示","非原始数据项，不执行数据库删除操作");
                                                                store_grid_QueryActionMap.remove(deleterecord);
                                                                store_grid_QueryActionMap.modified.remove(deleterecord);
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
                                    var modified = store_grid_QueryActionMap.modified;  
                                    updateData_QueryActionMap(modified); 
                                    store_grid_QueryActionMap.modified=[]
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
                    return customtableQueryActionManagerPanel;
                }
                
                function initTreePanel_QueryActionMap(){
                    treePanel_QueryActionMap = new Ext.tree.TreePanel({
                        region: 'center',
                        border:false,
                        autoScroll: true,
                        lines:false, 
                        rootVisible: false,
                        root:new Ext.tree.AsyncTreeNode({
                            loader: new Ext.tree.TreeLoader({dataUrl:'/DeviceManageSystem/CustomtablecolumnManager/LoadDataToTreePanel.action'}),
                            draggable:false,
                            id:'888888888'
                        }),
                        listeners: {
                            click: function(n) {                        
                                idCustomTable=n.attributes.id;
                                treePanel_QueryActionEdit.getLoader().dataUrl='/DeviceManageSystem/CustomtablequeryactioneditManager/LoadDataToTreePanel.action?idCustomTable='+idCustomTable;
                                treePanel_QueryActionEdit.root.reload();
                            }
                        }
                    });
                }
                 
                
  
                function deleteData_QueryActionMap(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/CustomtablequeryactionmapManager/DeleteDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json)},  
                            method: "POST",  
                            success: function(response) {},  
                            failure: function(response) {Ext.Msg.alert("警告", "删除数据错误！");}  
                        });  
                    }  
                    else {  
                        Ext.Msg.alert("警告", "没有任何需要删除的数据！");  
                    }  
                } 
                
                function updateData_QueryActionMap(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/CustomtablequeryactionmapManager/UpdateDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json)},  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() { store_grid_QueryActionMap.reload({params:{idCustomTable:idCustomTable}});});  
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
                
                
                
                function initJsonReader_QueryActionEdit(){
                    TableRecord_QueryActionEdit=Ext.data.Record.create([
                        {name:"idCustomQuerySubAction",type:"int",mapping:"idCustomQuerySubAction"},
                        {name:"idCustomQueryAction",type:"int",mapping:"idCustomQueryAction"},
                        {name:"property",type:"string",mapping:"property"},
                        {name:"querycase",type:"string",mapping:"querycase"},
                        {name:"queryvalue",type:"string",mapping:"queryvalue"},
                        {name:"subactionrelation",type:"string",mapping:"subactionrelation"}
                    ]);
                    read_grid_QueryActionEdit=new Ext.data.JsonReader({
                        root:'root',
                        totalProperty:'totalProperty',    
                        fields:TableRecord_QueryActionEdit
                    });
                }
                
                function initHttpProxy_QueryActionEdit(){
                    proxy_grid_QueryActionEdit=new Ext.data.HttpProxy({
                        url:'/DeviceManageSystem/CustomtablequeryactioneditManager/LoadDataToGridPanel.action',
                        method : 'post' 
                    });
                }
                
                function initStore_QueryActionEdit(){
                    store_grid_QueryActionEdit=new Ext.data.Store({
                        autoLoad:true,  
                        reader:read_grid_QueryActionEdit,
                        proxy:proxy_grid_QueryActionEdit
                    });
                }
                
                function initColumnModel_QueryActionEdit(){
                    sm_QueryActionEdit = new Ext.grid.CheckboxSelectionModel();
                    rm_QueryActionEdit=new Ext.grid.RowNumberer();
                    store1_1=new Ext.data.Store({  //井号选择下拉框  
                        proxy: new Ext.data.HttpProxy({     
                            url: '/DeviceManageSystem/CustomtablequeryactioneditManager/LoadCustomtablecolumnNames.action?idCustomTable='+idCustomTable,
                            method : 'post' 
                        }),     
                        reader: new Ext.data.JsonReader({     
                            root:'root',
                            totalProperty:'totalProperty' 
                        }, [     
                            {name:'columnName',mapping:'columnName'},     
                            {name:'columnShowName',mapping:'columnShowName'}     
                        ])     
                    });
                    
                    cm_QueryActionEdit=new Ext.grid.ColumnModel(
                    {
                        defaults: {
                            sortable: true
                        },
                        columns:[
                            rm_QueryActionEdit,
                            sm_QueryActionEdit,
                            {
                                header:"属性",
                                dataIndex:"property",
                                width:130,
                                editor:new Ext.form.ComboBox({
                                    store:store1_1,
                                    displayField:'columnShowName',
                                    valueField:'columnName',
                                    listWidth:150,
                                    typeAhead: true,
                                    mode: 'remote',
                                    forceSelection: true,
                                    triggerAction: 'all',
                                    emptyText:'Select a state...',
                                    selectOnFocus:true
                                })
                            },
                            {
                                header:"条件",
                                dataIndex:"querycase",
                                width:130,
                                editor:new Ext.form.ComboBox({
                                    store: new Ext.data.ArrayStore({
                                        fields: ['type'],
                                        data : [["="],["<"],[">"],["<="],[">="],["<>"],["LIKE"],["NOT LIKE"],["IN"],["NOT IN"]
                                            ,["IS NULL"],["IS NOT NULL"]] 
                                    }),
                                    displayField:'type',
                                    valueField:'type',
                                    listWidth:150,
                                    typeAhead: true,
                                    mode: 'local',
                                    forceSelection: true,
                                    triggerAction: 'all',
                                    emptyText:'Select a state...',
                                    selectOnFocus:true
                                })
                            },
                            {
                                header:"查询值",
                                dataIndex:"queryvalue",
                                width:130,
                                editor:new Ext.grid.GridEditor(new Ext.form.TextArea ())
                            },{
                                header:"查询关联",
                                dataIndex:"subactionrelation",
                                width:130,
                                editor:new Ext.form.ComboBox({
                                    store: new Ext.data.ArrayStore({
                                        fields: ['type'],
                                        data : [["END"],["AND"],["OR"]] 
                                    }),
                                    displayField:'type',
                                    valueField:'type',
                                    listWidth:150,
                                    typeAhead: true,
                                    mode: 'local',
                                    forceSelection: true,
                                    triggerAction: 'all',
                                    emptyText:'Select a state...',
                                    selectOnFocus:true
                                })
                            }
                        ]
                    });
                }
                          
                function initEditorGridPanel_QueryActionEdit(){
                    grid_QueryActionEdit = new Ext.grid.EditorGridPanel({
                        region: 'center',
                        border:false,
                        store:store_grid_QueryActionEdit,
                        sm: sm_QueryActionEdit,
                        cm: cm_QueryActionEdit,
                        autoEncode:true 
                    });
                }
                
                function initCustomtableQueryActionEditPanel(){
                    customtableQueryActionEditPanel=new Ext.Panel({
                        border:false,
                        region: 'center',
                        layout:"fit",
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler : function(){
                                    var initValue={idCustomQuerySubAction:null,idCustomQueryAction:idCustomQueryAction,property:null,
                                        querycase:null,queryvalue:null,subactionrelation:null};
                                    var tablerecord = new TableRecord_QueryActionEdit(initValue);
                                    var view=grid_QueryActionEdit.getView();
                                    grid_QueryActionEdit.stopEditing();  
                                    var sm_QueryActionEdit=grid_QueryActionEdit.getSelectionModel(); 
                                    if(sm_QueryActionEdit.hasSelection()){
                                        for(var i=view.getRows().length-1;i>=0;i--){
                                            if(sm_QueryActionEdit.isSelected(i)){
                                                store_grid_QueryActionEdit.insert(i,tablerecord);
                                            }
                                        }
                                    }else{
                                        store_grid_QueryActionEdit.add(tablerecord);
                                    }
                                    tablerecord.dirty=true;
                                    tablerecord.modified=initValue;
                                    if(store_grid_QueryActionEdit.modified.indexOf(tablerecord)==-1){
                                        store_grid_QueryActionEdit.modified.push(tablerecord);
                                    }
                                    view.refresh();
                                }
                            },
                            {
                                xtype:"button",
                                text:"删除",
                                iconCls:"delete",
                                handler : function(){
                                    var sm_QueryActionEdit=grid_QueryActionEdit.getSelectionModel();
                                    var view=grid_QueryActionEdit.getView();
                                    if(sm_QueryActionEdit.hasSelection()){
                                        Ext.Msg.confirm("提示","是否删除选中行数据",function(btn)
                                        {              
                                            if(btn=="yes"){
                                                var json=[];
                                                for(var i=view.getRows().length-1;i>=0;i--){
                                                    if(sm_QueryActionEdit.isSelected(i)){
                                                        var deleterecord=store_grid_QueryActionEdit.getAt(i);
                                                        grid_QueryActionEdit.stopEditing();  
                                                        if(store_grid_QueryActionEdit.modified.indexOf(deleterecord)==-1){
                                                            //Ext.Msg.alert("提示","执行数据库删除操作");
                                                            json.push(deleterecord);
                                                            store_grid_QueryActionEdit.remove(deleterecord)
                                                        }else{
                                                            if(deleterecord.get('idCustomQuerySubAction')!=null){  
                                                                //Ext.Msg.alert("提示","原始数据项，执行数据库删除操作 "+"ID:"+deleterecord.get('IDCUSTOMTABLE'));
                                                                json.push(deleterecord);
                                                                store_grid_QueryActionEdit.remove(deleterecord);
                                                                store_grid_QueryActionEdit.modified.remove(deleterecord);
                                                            }else{
                                                                //Ext.Msg.alert("提示","非原始数据项，不执行数据库删除操作");
                                                                store_grid_QueryActionEdit.remove(deleterecord);
                                                                store_grid_QueryActionEdit.modified.remove(deleterecord);
                                                            }                  
                                                        }
                                                    }
                                                }
                                                if(json.length>0){
                                                    deleteData_QueryActionEdit(json);
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
                                    var modified = store_grid_QueryActionEdit.modified;  
                                    updateData_QueryActionEdit(modified); 
                                    store_grid_QueryActionEdit.modified=[]
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
                            },
                            '->',
                            {
                                xtype:"button",
                                text:"查询操作管理",
                                iconCls:"table_edit",
                                handler : function(){                              
                                    var customtableQueryActionManagerPanel=initCustomtableQueryActionManagerPanel();
                                    var win;
                                    if(!win){
                                        win = new Ext.Window({
                                            title: '查询操作',
                                            closable:true,
                                            maximizable:true,
                                            width:600,
                                            height:300,
                                            border:false,
                                            layout: 'border'
                                        });
                                        
                                        initColumnModel_QueryActionMap();
                                        initJsonReader_QueryActionMap();
                                        initHttpProxy_QueryActionMap();
                                        initStore_QueryActionMap();  
                                        store_grid_QueryActionMap.load({params:{idCustomTable:idCustomTable}});
                                        initEditorGridPanel_QueryActionMap();
                                        customtableQueryActionManagerPanel.removeAll();
                                        customtableQueryActionManagerPanel.add(grid_QueryActionMap);
                                        customtableQueryActionManagerPanel.doLayout();
                                        
                                        win.add(customtableQueryActionManagerPanel);
                                        win.show(this);
                                    }
                                }
                            }
                        ]
                    }); 
                }
                
                function initTreePanel_QueryActionEdit(p){
                    treePanel_QueryActionEdit = new Ext.tree.TreePanel({
                        region: 'west',
                        title:"查询操作",
                        border:false,
                        autoScroll: true,
                        lines:false, 
                        split:true,
                        width:210,
                        loader: new Ext.tree.TreeLoader({dataUrl:'/DeviceManageSystem/CustomtablequeryactioneditManager/LoadDataToTreePanel.action?idCustomTable='+idCustomTable}),
                        rootVisible: false,
                        root:new Ext.tree.AsyncTreeNode({
                            draggable:false,
                            id:'888888888'
                        }),
                        listeners: {
                            click: function(n) { 
                                idCustomQueryAction=n.attributes.id;
                                initColumnModel_QueryActionEdit();
                                initJsonReader_QueryActionEdit();
                                initHttpProxy_QueryActionEdit();
                                initStore_QueryActionEdit();  
                                store_grid_QueryActionEdit.load({params:{idCustomQueryAction:idCustomQueryAction}});
                                initEditorGridPanel_QueryActionEdit();
                                p.removeAll();
                                p.add(grid_QueryActionEdit);
                                p.doLayout();
                                grid_QueryActionEdit.getView().refresh();
                            }
                        }
                    });
                }
                 
  
                function deleteData_QueryActionEdit(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/CustomtablequeryactioneditManager/DeleteDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idCustomQueryAction:idCustomQueryAction},  
                            method: "POST",  
                            success: function(response) {},  
                            failure: function(response) {Ext.Msg.alert("警告", "删除数据错误！");}  
                        });  
                    }  
                    else {  
                        Ext.Msg.alert("警告", "没有任何需要删除的数据！");  
                    }  
                } 
                
                function updateData_QueryActionEdit(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/DeviceManageSystem/CustomtablequeryactioneditManager/UpdateDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),idCustomQueryAction:idCustomQueryAction},  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() { store_grid_QueryActionEdit.reload({params:{idCustomQueryAction:idCustomQueryAction}});});  
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
                
                function createcustomtableQueryActionManagerPanel(){
                    initCustomtableQueryActionEditPanel();
                    initTreePanel_QueryActionEdit(customtableQueryActionEditPanel);
                    initTreePanel_QueryActionMap();
                
                    var customtableQueryActionManagerPanel=new Ext.Panel({
                        closable:true,
                        width: document.body.clientWidth,
                        height:document.body.clientHeight,
                        layout:'border',//表格布局  
                        renderTo : "content", 
                        autoWidth : true,
                        items:[
                            {
                                xtype:"panel",
                                title:"数据表",
                                region:"west",
                                collapsible:true,
                                layout:"border",
                                border:false,
                                width:210,
                                split:true,
                                items:[treePanel_QueryActionMap]
                            },
                            {
                                xtype:"panel",
                                region:"center",
                                layout:"border",
                                border:false,
                                items:[treePanel_QueryActionEdit,customtableQueryActionEditPanel]
                            }
                        ]
                    });       
                    return customtableQueryActionManagerPanel;
                }
                
                var customtableQueryActionManagerPanel=createcustomtableQueryActionManagerPanel();
                
                Ext.EventManager.onWindowResize(function(){ 
                    customtableQueryActionManagerPanel.setWidth(document.body.clientWidth);
                    customtableQueryActionManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>


