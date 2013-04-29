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
                var tableType;

                var TableRecord=Ext.data.Record.create([
                    {name:"idCommonTableColumn",type:"int",mapping:"idCommonTableColumn"},
                    {name:"columnName",type:"string",mapping:"columnName"},
                    {name:"columnShowName",type:"string",mapping:"columnShowName"},
                    {name:"columnIsHide",type:"boolean",mapping:"columnIsHide"},
                    {name:"columnIsPrimaryKey",type:"boolean",mapping:"columnIsPrimaryKey"},
                    {name:"columnType",type:"string",mapping:"columnType"},
                    {name:"columnRestrict",type:"string",mapping:"columnRestrict"},
                    {name:"columnDefualtValue",type:"string",mapping:"columnDefualtValue"},
                    {name:"columnControlType",type:"string",mapping:"columnControlType"},
                    {name:"columnControlDefine",type:"string",mapping:"columnControlDefine"},
                    {name:"columnControlValueType",type:"string",mapping:"columnControlValueType"},
                    {name:"tableType",type:"int",mapping:"tableType"},
                    {name:"comment",type:"string",mapping:"comment"},
                ]);
                
                var read_grid=new Ext.data.JsonReader({
                    root:'root',
                    tobalProperty:'tobalProperty',    
                    fields:TableRecord
                });
                          
                var proxy_grid=new Ext.data.HttpProxy({
                    url:'/project1/CommontablecolumnManager/LoadDataToGridPanel.action',
                    method : 'post' 
                });
                
                var store_grid=new Ext.data.GroupingStore({  
                    reader:read_grid,
                    proxy:proxy_grid
                });
                          
                
                var sm = new Ext.grid.CheckboxSelectionModel();
                var rm=new Ext.grid.RowNumberer();  
                var cm=new Ext.grid.ColumnModel(
                {
                    defaults: {
                        sortable: true
                    },
                    columns:[
                        rm,
                        sm,
                        {
                            header:"字段名",
                            dataIndex:"columnName",
                            width:80,
                            editor:new Ext.grid.GridEditor(new Ext.form.TextField())
                        },
                        {
                            header:"字段显示名",
                            dataIndex:"columnShowName",
                            width:80,
                            editor:new Ext.grid.GridEditor(new Ext.form.TextField())
                        },
                        {
                            header:"隐藏字段",
                            dataIndex:"columnIsHide",
                            width:70,
                            editor:new Ext.grid.GridEditor(new Ext.form.Checkbox())
                        },
                        {
                            header:"是否主键",
                            dataIndex:"columnIsPrimaryKey",
                            width:70,
                            editor:new Ext.grid.GridEditor(new Ext.form.Checkbox())
                        },
                        {
                            header:"字段类型",
                            dataIndex:"columnType",
                            width:80,
                            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
                                store: new Ext.data.ArrayStore({
                                    fields: ['type'],
                                    data : [["TEXT"],["DATE"],["DATETIME"],["TIME"],["YEAR"],["DECIMAL"],["TIME"],["INT"],
                                        ["BIGINT"],["DOUBLE"],["FLOAT"],["TINYINT"],["BOOL"],["BOOLEAN"],["BIT"],["BINARY"],
                                        ["LONG"],["CHAR"],["VARCHAR(45)"],["VARCHAR(500)"],["VARCHAR(1000)"]] 
                                }),
                                displayField:'type',
                                valueField:'type',
                                typeAhead: true,
                                mode: 'local',
                                forceSelection: true,
                                triggerAction: 'all',
                                listWidth:250,
                                emptyText:'Select a state...',
                                selectOnFocus:true
                            }))
                        },
                        {
                            header:"字段约束",
                            dataIndex:"columnRestrict",
                            width:120,
                            editor:new Ext.grid.GridEditor(new Ext.form.ComboBox({
                                store: new Ext.data.ArrayStore({
                                    fields: ['type'],
                                    data : [["NULL"],["NOT NULL"],["NOT NULL UNIQUE"],["NOT NULL AUTO_INCREMENT"]] 
                                }),
                                displayField:'type',
                                valueField:'type',
                                typeAhead: true,
                                mode: 'local',
                                forceSelection: true,
                                triggerAction: 'all',
                                listWidth:250,
                                emptyText:'Select a state...',
                                selectOnFocus:true
                            }))
                        },
                        {
                            header:"字段初始值",
                            dataIndex:"columnDefualtValue",
                            width:120,
                            editor:new Ext.grid.GridEditor(new Ext.form.TextArea ())
                        },
                        {
                            header:"控件类型",
                            dataIndex:"columnControlType",
                            width:80,
                            editor:new Ext.form.ComboBox({
                                store: new Ext.data.ArrayStore({
                                    fields: ['type'],
                                    data : [["TextField"],["TextArea"],["Checkbox"],["DateField"],["ComboBox"]] 
                                }),
                                displayField:'type',
                                typeAhead: true,
                                mode: 'local',
                                forceSelection: true,
                                triggerAction: 'all',
                                listWidth:250,
                                emptyText:'Select a state...',
                                selectOnFocus:true
                            })
                        },
                        {
                            header:"控件定义",
                            dataIndex:"columnControlDefine",
                            width:120,
                            editor:new Ext.grid.GridEditor(new Ext.form.TextArea ())
                        },
                        {
                            header:"控件值类型",
                            dataIndex:"columnControlValueType",
                            width:80,
                            editor:new Ext.form.ComboBox({
                                store: new Ext.data.ArrayStore({
                                    fields: ['type'],
                                    data : [["int"],["string"],["boolean"],["date"]] 
                                }),
                                displayField:'type',
                                valueField:'type',
                                typeAhead: true,
                                mode: 'local',
                                forceSelection: true,
                                triggerAction: 'all',
                                listWidth:250,
                                emptyText:'Select a state...',
                                selectOnFocus:true
                            })
                        },
                        {
                            header:"备注",
                            dataIndex:"comment",
                            width:170,
                            editor:new Ext.grid.GridEditor(new Ext.form.TextArea ())
                        }
                    ]
                });
                            
                var grid = new Ext.grid.EditorGridPanel({
                    region: 'center',
                    border:false,
                    store:store_grid,
                    sm: sm,
                    cm: cm,
                    view: new Ext.grid.GroupingView({
                        groupByText: '使用当前字段进行分组',
                        showGroupsText: '表格分组',
                        groupTextTpl: '{text}({[values.rs.length]}条记录)'
                    }),
                    autoEncode:true,
                    tbar:[
                        {
                            xtype:"button",
                            text:"新增",
                            iconCls:"add",
                            handler : function(){
                                var initValue={idCommonTableColumn:null,columnName:"idTest",columnShowName:"公共字段",columnIsHide:false,
                                    columnIsPrimaryKey:false,columnType:"INT",columnRestrict:"NULL",columnDefualtValue:null,columnControlType:"TextField",
                                    columnControlDefine:"{}",columnControlValueType:"int",tableType:tableType,comment:"公共字段"};
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
                                                        if(deleterecord.get('idCommonTableColumn')!=null){  
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
  
                function deleteData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });  
                    if (json.length > 0) {  
                        Ext.Ajax.request({  
                            url: "/project1/CommontablecolumnManager/DeleteDataToDb.action",  
                            params: {data:Ext.util.JSON.encode(json)},  
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
                            url: "/project1/CommontablecolumnManager/UpdateDataToDb.action",  
                            params: { data: Ext.util.JSON.encode(json),tableType:tableType},  
                            method: "POST",  
                            success: function(response) {  
                                Ext.Msg.alert("信息", "数据更新成功！", function() { store_grid.reload();});  
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
                
                var tree = new Ext.tree.TreePanel({
                    region: 'center',
                    border:false,
                    autoScroll: true,
                    lines:false, 
                    rootVisible: false,
                    loader: new Ext.tree.TreeLoader(),
                    root:new Ext.tree.AsyncTreeNode({
                        draggable:false,
                        id:'888888888',
                        children:[
                            {
                                id:1,
                                text: '易耗品类',
                                leaf: true
                            }, {
                                id:2,
                                text: '非易耗品类',
                                leaf: true
                            }
                        ]
                    }),
                    listeners: {
                        click: function(n) {
                            tableType=n.attributes.id;
                        }
                    }
                });
                tree.on("click",function(n,e) 
                {
                    store_grid.load({params:{tableType:n.attributes.id}});
                    grid.getView().refresh();
                });
               
                var tablesFieldsManagerPanel=new Ext.Panel({
                    closable:true,
                    width: document.body.clientWidth,
                    height:document.body.clientHeight,
                    layout:'border',//表格布局  
                    renderTo : "content", 
                    autoWidth : true,
                    items:[
                        {
                            xtype:"panel",
                            title:"数据表类别",
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
                            items:[grid]
                        }
                    ]
                });               
                
                Ext.EventManager.onWindowResize(function(){ 
                    tablesFieldsManagerPanel.setWidth(document.body.clientWidth);
                    tablesFieldsManagerPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>


