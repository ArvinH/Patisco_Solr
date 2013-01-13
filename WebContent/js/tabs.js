
function reloadPage()
  {
  window.location.reload()
  }

Ext.require('Ext.tab.*');


Ext.onReady(function() {
	var tabs2 = Ext.createWidget('tabpanel', {
		renderTo : document.body,
		activeTab : 0,
		width : 600,
		height :400,
		plain : true,
		defaults : {
			autoScroll : true,
			bodyPadding : 40
		},
		items : [ {
			title : 'Group 1',
			loader : {
				url : 'Group1.jsp',
				contentType : 'jsp',
				loadMask : true
			},
			listeners : {
				activate : function(tab) {
					tab.loader.load();
				}
			}
		}, {
			title : 'Group 2',
			loader : {
				url : 'Group2.jsp',
				contentType : 'jsp',
				loadMask : true
			},
			listeners : {
				activate : function(tab) {
					tab.loader.load();
				}
			}
		}, ]
	});
});