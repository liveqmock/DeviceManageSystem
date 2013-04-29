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
                var idCustomTable=null;
                var idCustomFunction=null;
                var TableRecord=null;
                var read_grid=null;
                var proxy_grid=null;
                var store_grid=null;
                var columnName="";
                var searchValue="";
                var data=null;
                var sm=null;
                var rm=null;
                var cm=null;
                var grid=null;
                
                function initJsonReader(){
                    Ext.Ajax.request(
                    {
                        url:'/DeviceManageSystem/DeviceFreeQuery/LoadGridRecord.action?idCustomTable='+idCustomTable,
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
                        failure:function(){Ext.Msg.alert("消息","创建Record失败");}
                    })
                }
                
                function initHttpProxy(){
                    proxy_grid=new Ext.data.HttpProxy({
                        url:'/DeviceManageSystem/DeviceFreeQuery/LoadGridData.action',
                        method : 'post' 
                    });
                }
                
                function initColumnModel(){
                    sm = new Ext.grid.CheckboxSelectionModel();
                    rm=new Ext.grid.RowNumberer();
                    Ext.Ajax.request(
                    {
                        url:'/DeviceManageSystem/DeviceFreeQuery/LoadGridColumn.action?idCustomTable='+idCustomTable,
                        success:function(response,option){
                            var json=response.responseText;
                            json = "[rm,sm," + json + "]";
                            cm=new Ext.grid.ColumnModel(eval(json));
                        },
                        failure:function(){Ext.Msg.alert("消息","创建ColumnModel失败");}
                    });
                }
                 
                function initStore(){
                    store_grid=new Ext.data.GroupingStore({
                        autoLoad:true,  
                        reader:read_grid,
                        proxy:proxy_grid
                    });
                    store_grid.on('beforeload',function(){this.baseParams ={idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15,data:data}});
                }
                
                function initGridPanel(){
                    grid = new Ext.grid.GridPanel({
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
                            emptyMsg: "没有要显示的数据"
                        })
                    }); 
                }         

                function submitData(modified) {  
                    var json = [];  
                    Ext.each(modified, function(item) {  
                        json.push(item.data);  
                    });   
                    if (json.length > 0) {  
                        data=Ext.util.JSON.encode(json);
                        store_grid.reload({params:{data:data,idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15}});               
                    }  
                    else {  
                        Ext.Msg.alert("警告", "没有输入查询条件！");  
                    }  
                }
                
                function createExcel(){
                    window.location.href = '/DeviceManageSystem/DeviceFreeQuery/CreateExcel.action';
                }
                
                //自定义查询
                var p1_1=new Ext.Panel({
                    region: 'center',
                    layout: 'fit',
                    border:false,
                    tbar:[
                        {
                            xtype:"button",
                            text:"自定义查询",
                            iconCls:"search",
                            handler:function(){
                                var win;
                                var record1_1=Ext.data.Record.create([
                                    {name:"columnName",type:"string",mapping:"columnName"},
                                    {name:"queryCase",type:"string",mapping:"queryCase"},
                                    {name:"value",type:"string",mapping:"value"},
                                    {name:"relation",type:"string",mapping:"relation"},
                                ]);
                
                                var store1_1=new Ext.data.Store({ 
                                    reader:new Ext.data.JsonReader({
                                        root:'root',
                                        totalProperty:'totalProperty',    
                                        fields:record1_1
                                    }),
                                    proxy:new Ext.data.HttpProxy({
                                        url:'/DeviceManageSystem/CustomfunctionManager/LoadDataToGridPanel.action',
                                        method : 'post' 
                                    })
                                });       
                
                                var store1_1_1=new Ext.data.Store({  //井号选择下拉框  
                                    proxy: new Ext.data.HttpProxy({     
                                        url: '/DeviceManageSystem/DeviceFreeQuery/LoadCustomtablecolumnNames.action?idCustomTable='+idCustomTable,
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
                            
                                var sm1_1= new Ext.grid.CheckboxSelectionModel();
                                var rm1_1=new Ext.grid.RowNumberer();
                                var cm1_1=new Ext.grid.ColumnModel(
                                {
                                    defaults: {
                                        sortable: true
                                    },
                                    columns:[   
                                        rm1_1,
                                        sm1_1,
                                        {
                                            header:"属性",
                                            dataIndex:"columnName",
                                            width:130,
                                            editor:new Ext.form.ComboBox({
                                                store:store1_1_1,
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
                                            dataIndex:"queryCase",
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
                                            dataIndex:"value",
                                            width:130,
                                            editor:new Ext.grid.GridEditor(new Ext.form.TextArea ())
                                        },{
                                            header:"查询关联",
                                            dataIndex:"relation",
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
                
                                var grid1_1 = new Ext.grid.EditorGridPanel({
                                    store: store1_1,
                                    sm: sm1_1,
                                    cm: cm1_1,
                                    region:"center",
                                    border:false,
                                    tbar:[
                                        {
                                            xtype:"button",
                                            text:"新增",
                                            iconCls:"add",
                                            handler : function(){
                                                var initValue={columnName:null,queryCase:null,value:null,relation:null};
                                                var tablerecord = new record1_1(initValue);
                                                var view=grid1_1.getView();
                                                grid1_1.stopEditing();  
                                                store1_1.add(tablerecord);
                                                tablerecord.dirty=true;
                                                tablerecord.modified=initValue;
                                                if(store1_1.modified.indexOf(tablerecord)==-1){
                                                    store1_1.modified.push(tablerecord);
                                                }
                                                view.refresh();
                                            }
                                        },
                                        {
                                            xtype:"button",
                                            text:"删除",
                                            iconCls:"delete",
                                            handler : function(){
                                                var sm=grid1_1.getSelectionModel();
                                                var view=grid1_1.getView();
                                                if(sm.hasSelection()){
                                                    Ext.Msg.confirm("提示","是否删除选中行数据",function(btn)
                                                    {              
                                                        if(btn=="yes"){
                                                            var json=[];
                                                            for(var i=view.getRows().length-1;i>=0;i--){
                                                                if(sm.isSelected(i)){
                                                                    var deleterecord=store1_1.getAt(i);
                                                                    grid1_1.stopEditing();  
                                                                    store1_1.remove(deleterecord);
                                                                    store1_1.modified.remove(deleterecord);
                                                                }
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
                                            text:"执行",
                                            iconCls:"submit",
                                            handler: function() {  
                                                columnName="";
                                                searchValue="";
                                                var modified = store1_1.modified;  
                                                submitData(modified); 
                                            }
                                        }
                                    ]
                                });
                                
                                if(!win){
                                    win = new Ext.Window({
                                        title: '自定义查询',
                                        closable:true,
                                        maximizable:true,
                                        width:600,
                                        height:300,
                                        border:false,
                                        layout: 'border',
                                        items: [grid1_1]
                                    });
                                    win.show(this);
                                }
                            }
                        },
                        {
                            xtype:"button",
                            text:"已定义查询",
                            iconCls:"search1"
                        },{
                            xtype:"button",
                            text:"导出Excel",
                            iconCls:"get_excel",
                            handler:function(){
                                createExcel();
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
                                        url: '/DeviceManageSystem/DeviceFreeQuery/LoadCustomtablecolumnNames.action?idCustomTable='+idCustomTable,
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
                                                    Ext.getCmp('label1').getEl().parent().first().dom.innerHTML=Ext.get('comb').dom.value+"：";
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
                            id:'label1',
                            xtype:"label",
                            width:120
                        }, 
                        {
                            id:"textfield1",
                            width:170,
                            xtype:"textfield"
                        },
                        {
                            xtype:"button",
                            text:"查找",
                            iconCls:"find",
                            handler:function(){
                                data=null;
                                searchValue=Ext.getCmp("textfield1").getValue();
                                store_grid.reload({params:{idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,data:data,start:0,limit:15}
                                });
                            }
                        }
                    ]
                })
                
                //自定义查询
                var tr1=new Ext.tree.TreePanel({
                    region: 'center',
                    border:false,
                    autoScroll: true,
                    lines:false, 
                    rootVisible: false,
                    loader: new Ext.tree.TreeLoader({dataUrl:'/DeviceManageSystem/DeviceFreeQuery/LoadDataToTreePanel.action'}),
                    root:new Ext.tree.AsyncTreeNode({
                        draggable:false,
                        id:'888888888'
                    }),
                    listeners: {
                        click: function(n) {
                            idCustomTable=n.attributes.id;
                            columnName="";
                            searchValue="";
                            data=null,
                            initColumnModel();
                            initJsonReader();
                            initHttpProxy();            
                            setTimeout(function(){
                                initStore();  
                                store_grid.reload({params:{data:data,idCustomTable:idCustomTable,columnName:columnName,searchValue:searchValue,start:0,limit:15}});
                                initGridPanel();
                                p1_1.removeAll();
                                p1_1.add(grid);
                                p1_1.doLayout();
                                initNewValue();
                            },
                            500);
                        }
                    }
                });   

                var p1=new Ext.Panel({     
                    region:"center",
                    layout:"border",
                    border:false,
                    items:[
                        {
                            xtype:"panel",
                            title:"物品",
                            region:"west",
                            collapsible:true,
                            layout:"border",
                            border:false,
                            width:210,
                            split:true,
                            items:[tr1]
                        },p1_1]      
                });
    
                var devicesQueryPanel=new Ext.Panel({
                    closable:true,
                    width: document.body.clientWidth,
                    height:document.body.clientHeight,
                    layout:'border',//表格布局  
                    renderTo : "content", 
                    autoWidth : true,
                    items:[p1]
                });
                Ext.EventManager.onWindowResize(function(){ 
                    devicesQueryPanel.setWidth(document.body.clientWidth);
                    devicesQueryPanel.setHeight(document.body.clientHeight);
                }) 
            });
        </script>
    </head>
    <body>
        <div id="content">
        </div>
    </body>