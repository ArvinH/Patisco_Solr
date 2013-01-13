
Ext.onReady(function(){

	var proxy=new Ext.data.HttpProxy({url:'test.jsp'});
	
		  var reader=new Ext.data.JsonReader(
			{
			},[
				{name: 'id', mapping: 'id'},
				{name: 'Specification'}           
			]
		);
 
		var store=new Ext.data.Store(    {
		  proxy:proxy,
		  reader:reader
	   });

	store.load();


    // create the grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: "id", width: 60, dataIndex: 'id', sortable: true},
            {header: "Specification", width: 500, dataIndex: 'Specification', sortable: true}
        ],
        renderTo:'example-grid',
        width:540,
        height:200,
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: store,
            displayInfo: true,
            emptyMsg: "沒有記錄"
        })
    });
    grid.render();
    store.load({params:{start:0, limit:10}});
});