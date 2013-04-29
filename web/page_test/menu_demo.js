/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
{
    text: "历史",
    iconCls:"history",
    menu: new Ext.menu.Menu({
        allowOtherMenus: true,
        items: [
            {
                xtype:"menuitem",
                text:"入库历史",
                handler:function(){
                    var win;
                    var nav = new Ext.Panel({
                        region: 'center'
                    });
                    if(!win){
                        win = new Ext.Window({
                            title: '入库历史',
                            closable:true,
                            maximizable:true,
                            width:700,
                            height:400,
                            border:false,
                            layout: 'border',
                            items: [nav]
                        });
                        win.show(this);
                    }
                }
            },{
                xtype:"menuitem",
                text:"移交历史",
                handler:function(){
                    var win;
                    var nav = new Ext.Panel({
                        region: 'center'
                    });
                    if(!win){
                        win = new Ext.Window({
                            title: '移交历史',
                            closable:true,
                            maximizable:true,
                            width:700,
                            height:400,
                            border:false,
                            layout: 'border',
                            items: [nav]
                        });
                        win.show(this);
                    }
                }
            },{
                xtype:"menuitem",
                text:"退运历史",
                handler:function(){
                    var win;
                    var nav = new Ext.Panel({
                        region: 'center'
                    });
                    if(!win){
                        win = new Ext.Window({
                            title: '退运历史',
                            closable:true,
                            maximizable:true,
                            width:700,
                            height:400,
                            border:false,
                            layout: 'border',
                            items: [nav]
                        });
                        win.show(this);
                    }
                }
            }
        ]
    })
},

