/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
{
    store: new Ext.data.ArrayStore({
        fields: ['displayName'],
        data : [['松下'],['SONY'],['三菱']] 
    }),
    valueField:'displayName',
    displayField:'displayName',
    typeAhead: true,
    mode: 'local',
    forceSelection: true,
    triggerAction: 'all',
    emptyText:'Select a state...',
    selectOnFocus:true
}

