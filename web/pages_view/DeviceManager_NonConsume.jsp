<%-- 
    Document   : DevicesManager
    Created on : 2013-2-17, 14:08:38
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
            /* 
             * To change this template, choose Tools | Templates
             * and open the template in the editor.
             */
            Ext.onReady(function(){ 
                var idCustomTable;
                var TableRecord=null;
                var read_grid=null;
                var proxy_grid=null;
                var store_grid=null;
                var newValue=null;
                var columnName="";
                var searchValue="";
                var sm=null;
                var rm=null;
                var cm=null;
                var grid=null;
    
                function initJsonReader(){
                    Ext.Ajax.request(
                    {
                        url:'/project1/DeviceManager/LoadGridRecord.action?idCustomTable='+idCustomTable,
                        success:function(response,option){
                            var json=response.responseText;
                            json = "[" + json + "]";
                            TableRecord=Ext.data.Record.create(eval(json));
                            read_grid=new Ext.data.JsonReader({
                                root:'root',
                                totalProperty:'totalProperty',    
                                fields:TableRecord
                            });
                        },
                        failure:function(){
                            Ext.Msg.alert("消息","创建Record失败");
                        }
                    })
                }
                
                function initHttpProxy(){
                    proxy_grid=new Ext.data.HttpProxy({
                        url:'/project1/DeviceManager/LoadGridData.action',
                        method : 'post' 
                    });
                }
                
                function initColumnModel(){
                    sm = new Ext.grid.CheckboxSelectionModel();
                    rm=new Ext.grid.RowNumberer();
                    Ext.Ajax.request(
                    {
                        url:'/project1/DeviceManager/LoadGridColumn.action?idCustomTable='+idCustomTable,
                        success:function(response,option){
                            var json=response.responseText;
                            json = "[rm,sm," + json + "]";
                            cm=new Ext.grid.ColumnModel(eval(json));
                        },
                        failure:function(){
                            Ext.Msg.alert("消息","创建ColumnModel失败");
                        }
                    });
                }
                 
                function initStore(){
                    store_grid=new Ext.data.GroupingStore({
                        autoLoad:true,  
                        reader:read_grid,
                        proxy:proxy_grid
                    });
                    store_grid.on('beforeload',function(){this.baseParams ={idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15};});
                }
            
                function initEditorGridPanel(){
                    grid = new Ext.grid.EditorGridPanel({
                        border:false,
                        store:store_grid,
                        sm: sm,
                        cm: cm,
                        autoEncode:true,
                        view: new Ext.grid.GroupingView({
                            groupByText: '使用当前字段进行分组',
                            showGroupsText: '表格分组',
                            groupTextTpl: '{text}({[values.rs.length]}条记录)'
                        }),
                        bbar: new Ext.PagingToolbar({
                            pageSize: 15,
                            store: store_grid,
                            displayInfo: true,
                            displayMsg: '显示 {0}-{1} 条记录，共 {2} 条',
                            emptyMsg: "No topics to display"
                        })
                    }); 
                }

                function initNewValue(){
                    Ext.Ajax.request(
                    {
                        url:'/project1/DeviceManager/CreateInitValue.action?idCustomTable='+idCustomTable,
                        success:function(response,option){
                            newValue=response.responseText;
                        },
                        failure:function(){
                            Ext.Msg.alert("消息","失败");
                        }
                    })
                }
    
                function deleteData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/project1/DeviceManager/DeleteDataToDb.action",  
                            params: {
                                data: Ext.util.JSON.encode(json),idCustomTable:idCustomTable
                            },  
                            method: "POST",  
                            success: function(response) {},  
                            failure: function(response) {
                                Ext.Msg.alert("警告", "删除数据错误！");
                            }  
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
                            url: "/project1/DeviceManager/UpdateDataToDb.action",  
                            params: {
                                data: Ext.util.JSON.encode(json),
                                idCustomTable:idCustomTable
                            },  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() {
                                    store_grid.reload({params:{idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15}});
                                });  
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
    
                function NonConsumeDevicePanel_deviceManager(){
                    //非易耗品台账编辑面板
                    var p2_1=new Ext.Panel({
                        border:false,
                        region: 'center',
                        layout:"fit",
                        tbar:[
                            {
                                xtype:"button",
                                text:"新增",
                                iconCls:"add",
                                handler:function(){
                                    var initValue=eval("("+newValue+")");
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
                                        store_grid.insert(0,tablerecord);
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
                                                            if(deleterecord.get('ID')!=null){  
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
                                handler:function(){
                                    var modified = store_grid.modified;  
                                    updateData(modified); 
                                    store_grid.modified=[]
                                }
                            },
                            '-',
                            {
                                xtype:"button",
                                text:"操作",
                                iconCls:"applications",
                                handler:function(){
                                    var win;
                                    var tree=new Ext.tree.TreePanel({
                                        region: 'center',
                                        border:false,
                                        autoScroll: true,
                                        lines:false, 
                                        rootVisible: false,
                                        loader: new Ext.tree.TreeLoader({
                                            dataUrl:'/project1/TablefunctionMap/LoadDataToTreePanel.action?idCustomTable='+idCustomTable
                                        }),
                                        root:new Ext.tree.AsyncTreeNode({
                                            draggable:false,
                                            id:'888888888'
                                        }),
                                        listeners: {
                                            click: function(n) {
                                                
                                            }
                                        }
                                    });
                                    var table;
                                    var treepanel=new Ext.Panel({
                                        title:"操作",
                                        region:"west",
                                        layout:"border",
                                        border:false,
                                        split:true,
                                        collapsible:true,
                                        width:180,
                                        items:[tree]
                                    });
                                    var tablepanel=new Ext.Panel({
                                        region:"center",
                                        border:false,
                                        layout:"fit",
                                        tbar:[
                                            {
                                                xtype:"button",
                                                text:"执行",
                                                iconCls:"submit"
                                            },
                                            '-',
                                            {
                                                xtype:"button",
                                                text:"刷新",
                                                iconCls:"refresh"
                                            }
                                        ],
                                        items:[]
                                    });
                                    
                                    if(!win){
                                        win = new Ext.Window({
                                            xtype:"window",
                                            title:"数据表操作",
                                            width:650,
                                            height:350,
                                            maximizable:true,
                                            layout:"border",
                                            border:false,
                                            closable:true,
                                            hideBorders:true,
                                            items:[treepanel,tablepanel]
                                        });     
                                    }
                                    win.show();
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
                            "->",
                            '-',
                            {
                                xtype:"button",
                                iconCls:"config",
                                handler:function(){
                                    var win;
                                    var store_comb=new Ext.data.Store({  //井号选择下拉框  
                                        proxy: new Ext.data.HttpProxy({     
                                            url: '/project1/DeviceFreeQuery/LoadCustomtablecolumnNames.action?idCustomTable='+idCustomTable,
                                            method : 'post' 
                                        }),     
                                        reader: new Ext.data.JsonReader({     
                                            root:'root',
                                            totalProperty:'totalProperty' 
                                        }, [     
                                            {
                                                name:'columnName',
                                                mapping:'columnName'
                                            },     

                                            {
                                                name:'columnShowName',
                                                mapping:'columnShowName'
                                            }     
                                        ])     
                                    });
                                    var comb=new Ext.form.ComboBox({
                                        id:'comb',
                                        store:store_comb,
                                        displayField:'columnShowName',
                                        valueField:'columnName',
                                        listWidth:150,
                                        typeAhead: true,
                                        mode: 'remote',
                                        forceSelection: true,
                                        triggerAction: 'all',
                                        emptyText:'Select a state...',
                                        selectOnFocus:true
                                    });
                                    if(!win){
                                        win = new Ext.Window({
                                            id:"win",
                                            title:"选择查询项",
                                            width:225,
                                            height:55,
                                            layout:"border",
                                            hideBorders:true,
                                            resizable:false,
                                            tbar:[comb,
                                                {
                                                    xtype:"button",
                                                    text:"确定",
                                                    handler:function(){
                                                        columnName=Ext.getCmp('comb').getValue();
                                                        Ext.getCmp('label2').getEl().parent().first().dom.innerHTML=Ext.get('comb').dom.value+"：";
                                                        Ext.getCmp('win').close();
                                                    }
                                                }
                                            ]
                                        });
                                        win.show(this);
                                    }
                                }
                            },
                            {
                                id:'label2',
                                xtype:"label",
                                width:120
                            }, 
                            {
                                id:"textfield2",
                                width:170,
                                xtype:"textfield"
                            },
                            {
                                xtype:"button",
                                text:"查找",
                                iconCls:"find",
                                handler:function(){
                                    searchValue=Ext.getCmp("textfield2").getValue();
                                    store_grid.reload({params:{idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15}});
                                }
                            }                    
                        ]    
                    });
                    return p2_1;
                }
    
                function NonConsumeDevicePanel_deviceTree(p){
                    //非易耗品，type=2
                    var tr2=new Ext.tree.TreePanel({
                        region: 'center',
                        border:false,
                        autoScroll: true,
                        lines:false, 
                        rootVisible: false,
                        loader: new Ext.tree.TreeLoader({
                            dataUrl:'/project1/DeviceManager/LoadDataToTreePanel.action?customtabletype=2'
                        }),
                        root:new Ext.tree.AsyncTreeNode({
                            draggable:false,
                            id:'888888889'
                        }),
                        listeners: {
                            click: function(n) {
                                idCustomTable=n.attributes.id;
                                initColumnModel();
                                initJsonReader();
                                initHttpProxy();
                                setTimeout(function(){
                                    initStore();  
                                    store_grid.reload({params:{idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15}});
                                    initEditorGridPanel();
                                    p.removeAll();
                                    p.add(grid);
                                    p.doLayout();
                                    initNewValue();
                                },
                                500);
                            }
                        }
                    });
                    return tr2;
                }
    
                function createNonConsumeDevicePanel(){
                    var p2_1=NonConsumeDevicePanel_deviceManager();
                    var tr2=NonConsumeDevicePanel_deviceTree(p2_1);
                    
                    var p2=new Ext.Panel({
                        region:"center",
                        layout:"border",
                        border:false,      
                        items:[
                            {
                                xtype:"panel",
                                title:"物品",
                                region:"west",
                                layout:"border",
                                collapsible:true,
                                border:false,
                                split:true,
                                autoScroll:true,
                                width:210,
                                items:[tr2]
                            },p2_1]
                    });
        
                    return p2;
                }
    
                function createdevicesManagerPanel(){
                    var devicesManagerPanel=new Ext.Panel({
                        closable:true,
                        width: document.body.clientWidth,
                        height:document.body.clientHeight,
                        layout:'border',//表格布局  
                        renderTo : "content", 
                        autoWidth : true,
                        items:[createNonConsumeDevicePanel()]
                    });
                    return devicesManagerPanel;
                }
    
                var devicesManagerPanel=createdevicesManagerPanel()
    
                Ext.EventManager.onWindowResize(function(){ 
                    devicesManagerPanel.setWidth(document.body.clientWidth);
                    devicesManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });


        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>
