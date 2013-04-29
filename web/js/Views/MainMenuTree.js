function MainMenuTree(){
    var mainMenuTree=new Ext.tree.TreePanel({
        region: 'center',
        border:false,
        width: 210,
        autoScroll: true,
        split: true,
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            expanded: true,
            children: [{
                    text: '设备台账管理',
                    leaf: false,
                    expanded:true,
                    children: [{
                            text: '设备台账编辑(易耗品)',
                            leaf: true,
                            url:"../pages_view/DeviceManager_Consume.jsp"
                        },{
                            text: '设备台账编辑(非易耗品)',
                            leaf: true,
                            url:"../pages_view/DeviceManager_NonConsume.jsp"
                        },{
                            text: '设备台账查询',
                            leaf: true,
                            url:"../pages_view/DevicesQuery.jsp"
                        }/*,{
                            text: '设备资源图表',
                            leaf: true,
                            url:"../pages_view/DevicesChart.jsp"
                        }*/]
                }, {
                    text: '数据表管理',
                    leaf: false,
                    expanded:true,
                    children: [{
                            text: '数据表管理',
                            leaf: true,
                            url:"../pages_view/CustomtableManager.jsp"
                        },{
                            text: '数据项管理',
                            leaf: true,
                            url:"../pages_view/CustomtablecolumnManager.jsp"
                        },{
                            text: '公共项管理',
                            leaf: true,
                            url:"../pages_view/CommontablecolumnManager.jsp"
                        }]
                },{
                    text: '表操作管理',
                    leaf: false,
                    expanded:true,
                    children: [{
                            text: '查询操作管理',
                            leaf: true,
                            url:"../pages_view/CustomtableQueryActionManager.jsp"
                        },{
                            text: '自定操作管理',
                            leaf: true,
                            url:"../pages_view/CustomtableQueryActionManager.jsp"
                        }]
                },{
                    text: '系统管理',
                    leaf: false,
                    expanded:true,
                    children: [{
                            text: '组织机构编辑',
                            leaf: true,
                            url:"../pages_view/DepartmentManager.jsp"
                        },{
                            text: '用户信息编辑',
                            leaf: true,
                            url:"../pages_view/UserManager.jsp"
                        },{
                            text: '用户角色关联',
                            leaf: true,
                            url:"../pages_view/UserRoleManager.jsp"
                        },{
                            text: '系统角色定义',
                            leaf: true,
                            url:"../pages_view/RoleManager.jsp"
                        },{
                            text: '角色操作关联',
                            leaf: true,
                            url:"../pages_view/RoleActionManager.jsp"
                        },{
                            text: '模块类别管理',
                            leaf: true,
                            url:"../pages_view/ActionmodulegroupManager.jsp"
                        },{
                            text: '功能模块分类',
                            leaf: true,
                            url:"../pages_view/ActionmodulegroupmapManager.jsp"
                        }
                    ]
                }]
        }),
        rootVisible: false
        
});
return mainMenuTree;
};
   