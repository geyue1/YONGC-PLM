import Vue from 'vue';   
import ElementUI from 'element-ui'
import $ from 'jquery'
import 'vue/element-ui2.12.css'
import './css/main.css'


Vue.use(ElementUI)
document.onreadystatechange = function () {
	if(document.readyState=="complete"){//complete 加载完成
	var portalSearch = new Vue({
	      el: '#portal-search',
	      methods: {
	        searchPart : function (){
	            //this.searchInput
	            //后台搜索数据
	            this.searchTablevisible = true;
	            
	            var searchInput = $.trim($('#searchInput').val());
	            
	            var self = this;
	   		  	 var params={
	   		  			 action : 'SEARCH_PART',
	   		  			 searchInput : searchInput
	   		  	 };
	   		  $.ajax({
	                url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	                data:params,
	                cache : false,
	                async : false,
	                type : "POST",
	                success : function (res){
	                	var searchData = eval($.trim(res));
	                	self.searchData = searchData;
	                },
	                error : function(e){
	                    console.log(e.status);
	                    console.log(e.responseText);
	                }
	            });
	          }
	      },
	      data: function() {
	        return { 
	          searchTablevisible: false ,
	          rightTableHeight:(window.innerHeight-100),
	          searchInput:'',
	          searchTooltip:'',
	          searchBtnText:'',
	          advancedSearchBtnText:'',
	          opLinkBtnText:'操作快捷连接',
	          searchData:[],
	          resources:{
	        	  NUMBER:'',
	        	  NAME:'',
	        	  CONTAINER_NAME:'',
	        	  VERSION:'',
	        	  STATE:'',
	        	  MODIFY_TIME:'',
	        	  CREATE_TIME:''
	        	  
	          }
	          
	        }
	      }
	    })
	   var portalContent = new Vue({
	      el: '#portal-content',
	      data: function() {
	        return { 
	          moreDocTablevisible: false ,
	          rightTableHeight:(window.innerHeight-100),
	          myTaskDates:[],
	          noticeDates:[],
	          downloadDates:[],
	          docsDatas:[],
	          createApplyDates:[],
	          reportsDates:[],
	          selectionModelDates:[],
	          resources:{
	        	  myTaskMoreUrl:'',
	        	  noticekMoreUrl:'',
	        	  downloadMoreUrl:'',
	        	  refreshBtnToolTip:'',
	        	  MY_TASK:'',
	        	  CREATE_FORM:'',
	        	  MODEL_SELECTION_GUIDE:'',
	        	  NOTICE_CENTRE:'',
	        	  DOWNLOAD_CENTRE:'',
	        	  REPORT_CENTRE:'',
	        	  NUMBER:'',
	        	  NAME:'',
	        	  PRIMARY:'',
	        	  VERSION:'',
	        	  STATE:'',
	        	  DESC:'',
	        	  MODIFY_TIME:'',
	        	  CREATE_TIME:''
	          }
	          } 
	      },
	      methods: {
	    	  noticekMore: function(){
	    		  this.moreDocTablevisible = true;
	    		  
	    		  var self = this;
		   		  	 var params={
		   		  			 action : 'GET_MORE_NOTICE'
		   		  	 };
		   		  $.ajax({
		                url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
		                data:params,
		                cache : false,
		                async : false,
		                type : "POST",
		                success : function (res){
		                	var docsDatas = eval($.trim(res));
		                	self.docsDatas = docsDatas;
		                },
		                error : function(e){
		                    console.log(e.status);
		                    console.log(e.responseText);
		                }
		            });
	    	  },
	    	  downloadMore: function(){
	    		  this.moreDocTablevisible = true;
	    		  
	    		  var self = this;
		   		  	 var params={
		   		  			 action : 'GET_MORE_DOWNLOAD'
		   		  	 };
		   		  $.ajax({
		                url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
		                data:params,
		                cache : false,
		                async : false,
		                type : "POST",
		                success : function (res){
		                	var docsDatas = eval($.trim(res));
		                	self.docsDatas = docsDatas;
		                },
		                error : function(e){
		                    console.log(e.status);
		                    console.log(e.responseText);
		                }
		            });
	    	  },
	    	  refreshMyTask :   function() {
	    		 var self = this;
	   		  	 var params={
	   		  			 action : 'GET_MYTASK_DATA'
	   		  	 };
	   		  $.ajax({
	                url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	                data:params,
	                cache : false,
	                async : false,
	                type : "POST",
	                success : function (res){
	                	var myTaskDates = eval($.trim(res));
	                	self.myTaskDates = myTaskDates;
	                },
	                error : function(e){
	                    console.log(e.status);
	                    console.log(e.responseText);
	                }
	            });
	    	   },
	    	   refreshNotice :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'GET_NOTICE_DATA'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var noticeDates = eval($.trim(res));
	    				   self.noticeDates = noticeDates;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   refreshDownload :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'GET_DOWNLOAD_DATA'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var downloadDates = eval($.trim(res));
	    				   self.downloadDates = downloadDates;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   refreshResources :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'GET_RESOURCES'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var resources = $.parseJSON($.trim(res));
	    				   self.resources = resources;
	    				   portalSearch.searchTooltip = resources.searchTooltip
	    				   portalSearch.searchBtnText = resources.searchBtnText
	    				   portalSearch.advancedSearchBtnText = resources.advancedSearchBtnText
	    				   portalSearch.resources=resources;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   refreshCreateApply :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'CREATE_APPLY'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var createApplyDates = eval($.trim(res));
	    				   self.createApplyDates = createApplyDates;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   refreshReports :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'REPORT_DATA'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var reportsDates = eval($.trim(res));
	    				   self.reportsDates = reportsDates;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   refreshSelectionModel :   function() {
	    		   var self = this;
	    		   var params={
	    				   action : 'SELECTION_MODEL_DATA'
	    		   };
	    		   $.ajax({
	    			   url : getRootPath()+'/netmarkets/jsp/ext/rdc/standard/portal/action.jsp',
	    			   data:params,
	    			   cache : false,
	    			   async : false,
	    			   type : "POST",
	    			   success : function (res){
	    				   var selectionModelDates = eval($.trim(res));
	    				   self.selectionModelDates = selectionModelDates;
	    			   },
	    			   error : function(e){
	    				   console.log(e.status);
	    				   console.log(e.responseText);
	    			   }
	    		   });
	    	   },
	    	   initData : function(){
	    		   this.refreshResources();
	    		   this.refreshCreateApply();
	    		   this.refreshMyTask();
	    		   this.refreshNotice();
	    		   this.refreshDownload();
	    		   this.refreshReports();
	    		   this.refreshSelectionModel();
	    		   setTimeout(function(){
	    			   $('td.portal-link-td').each(function(t,n){
	    				   var nn = $(n);
	    				   nn.find('a').width(nn.width()-10);
	    			   });
	    			   $('td.portal-link-td2').each(function(t,n){
	    				   var nn = $(n);
	    				   nn.find('a').width(nn.parents('.portal-item-table').width());
	    			   });
	    			   
	    		   },300);
	    	   }
	     }
	    });
	    portalContent.initData();
	}
}

function getRootPath() {
    //获取当前网址，如： http://localhost:8088/test/test.jsp
    var curPath = window.document.location.href;
    //获取主机地址之后的目录，如： test/test.jsp
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8088
    var localhostPath = curPath.substring(0, pos);
    //获取带"/"的项目名，如：/test
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPath + projectName);//发布前用此
}
