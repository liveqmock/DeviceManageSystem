<%-- 
    Document   : newjsp
    Created on : 2013-3-16, 23:18:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
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
        Ext.onReady(function() {          
            var triggerfield=new Ext.form.TriggerField({
                                id:'idTri',
                                onSelect:function(record){
                                },
                                onTriggerClick:function(){
                                    function onmenucheck(item){
                                        menu.hide();
                                        Ext.getCmp("idTri").setValue(item.text);
                                    }
                                        
                                    var menu=new Ext.menu.Menu({
                                        items:[
                                            {text:'栏目1',handler:onmenucheck},
                                            {text:'栏目2',handler:onmenucheck},
                                            {text:'栏目3',handler:onmenucheck}
                                        ]
                                    });
                                    if(this.menu==null)
                                        this.menu=menu;
                                    this.menu.show(this.el,"tl-bl?");
                                }
                            })
            
            var form2=new Ext.form.FormPanel({
                title:'form2',
                frame:true,
                items:[triggerfield],
                renderTo:'content'
            })
        });

    </script>
    <body>
    <body>
        <div id="content"></div>
    </body>
</body>
</html>
