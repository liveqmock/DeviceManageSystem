Ext.onReady(function(){ 
    var x0 = new Ext.Panel({
        region:"north",
        border:false,
        height:95,
        html: '<table cellpadding="0" cellspacing="0" width="100%">'+
        '<tr>'+
        '<td>'+
        '<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
        '<tr>'+
        '<td width="411"><img src="../resource/01.jpg" width="411" height="80" /></td>'+
        '<td background="../resource/02.jpg">&nbsp;</td>'+
        '<td width="502"><img src="../resource/03.jpg" width="502" height="80"/></td>'+
        '</tr>'+
        '</table>'+
        '</td>'+
        '<td style="text-align:right;color:#ccc;display:none;">123456789&nbsp;</td>'+
        '</tr>'+
        '</table>',
        bbar:[
        {
            xtype:"button",
            text:"桌面",
            iconCls:"home" ,
            handler:function(){
                window.location.reload();
            }
        },
        {
            xtype:"button",
            text:"密码",
            iconCls:"passwordset"
        },
        {
            xtype:"button",
            text:"帮助",
            iconCls:"help"
        },
        {
            xtype:"button",
            text:"退出",
            iconCls:"exit"
        }
        ]
    });

    var x2_1=MainMenuTree();
     
    var x2=new Ext.Panel(
    {
        title:"主菜单",
        region:"west",
        layout:"border",
        width:210,
        collapsible:true,
        floatable:false,
        split:true,
        items:[x2_1]
    }
    );
               
    
    var x3=MainTabPanel();
    
    function treeClick(n,event){
        if(n.isLeaf()){
            event.stopEvent(); 
            var url=n.attributes.url;
            var id=n.attributes.id;
            if(url){
                if(x3.getComponent(id)){
                    x3.setActiveTab(id);
                }else{
                    var p=new Ext.Panel({
                        title:n.attributes.text,
                        id:id,
                        closable:true,
                        html:'<iframe id="test" src="'+url+'" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
                    });
                    x3.add(p);
                    x3.setActiveTab(p);
                }
            }
        }  
    }
    x2_1.on('click', treeClick);
    
    var x1=new Ext.Panel(
    {
        region:"center",
        layout:"border",
        border:false,
        items:[x2,x3]
    });
    
    
    var viewport1=new Ext.Viewport({
        xtype:"viewport",
        layout:"border",
        items:[x0,x1]
    });
    
    
                
});
