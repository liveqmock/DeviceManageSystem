/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function MainTabPanel(){
    var mainTabPanel=new Ext.TabPanel({
        region:"center",
        frame:true,
        enableTabScroll:true,
        plugins:new Ext.ux.TabCloseMenu()
    });
    return mainTabPanel;
}

