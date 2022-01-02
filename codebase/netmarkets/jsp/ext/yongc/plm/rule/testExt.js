Ext.onReady(function(){
	alert("123");
	 var txtName = new Ext.form.TextField({
		  name: "txtName",
		  fieldLabel: "姓名",
		  width: 200
		  });
	//密码
	 
	  var txtPassword = new Ext.form.TextField({
	  name: "txtPassword",
	  fieldLabel: "密码",
	  inputType: "password",
	  width: 200
	  });
	  //性别男
	  var rdaSexBoy = new Ext.form.Radio({
	  name: "rdaSex",
	  inputValue: "男",//实际值
	  boxLabel: "男",//显示值
	  checked: true //默认为男
	  });
	  //性别女
	  var rdaSexGirl = new Ext.form.Radio({
	  name: "rdaSex",
	  inputValue: "女",
	  boxLabel: "女",
	  width: 150
	  });
	  //性别分组
	  var grpSex = new Ext.form.RadioGroup({
	  name: "sex",
	  fieldLabel: "性别",
	  items: [rdaSexBoy, rdaSexGirl],
	  width: 100
	  })
	  //出生日期
	  var dateBirthday = new Ext.form.DateField({
	  name: "dateBirthday",
	  fieldLabel: "出生日期",
	  width: 200,
	  format: "Y-m-d",//指定日期格式,Y 表示四位数的年，m表示月，d表示日
	  value: new Date() //默认为当前日期
	  });
	  //爱好
	  var chkHobby1 = new Ext.form.Checkbox({
	  name: "chkHobby",
	  inputValue: "钓鱼",
	  boxLabel: "钓鱼",
	  checked: true
	  });
	  var chkHobby2 = new Ext.form.Checkbox({
	  name: "chkHobby",
	  inputValue: "网友聚会",
	  boxLabel: "网友聚会"
	  });
	  var chkHobby3 = new Ext.form.Checkbox({
	  name: "chkHobby",
	  inputValue: "洗桑拿",
	  boxLabel: "洗桑拿"
	  });
	  var chkHobby4 = new Ext.form.Checkbox({
	  name: "chkHobby",
	  inputValue: "打保龄球",
	  boxLabel: "打保龄球"
	  });
	  //爱好分组
	  var grpGobby = new Ext.form.CheckboxGroup({
	  name: "hobby",
	  fieldLabel: "您的爱好",
	  items: [chkHobby1, chkHobby2, chkHobby3, chkHobby4],
	  width: 300
	  });
	  //最高学历
	  //准备数据
	  var data = [[1, "博士"],
	  [2, "硕士"],
	  [3, "研究生"],
	  [4, "本科"],
	  [5, "专科"],
	  [6, "大学肄业"],
	  [7, "文盲"]];
	  var proxy = new Ext.data.MemoryProxy(data);
	  //定义学历结构
	  var Edu = Ext.data.Record.create([
	  {name: "eid", type: "int", mapping: 0},
	  {name: "ename", type: "string", mapping: 1}
	  ]);
	  var reader = new Ext.data.ArrayReader({}, Edu);
	  var store = new Ext.data.Store({
	  proxy: proxy,
	  reader: reader
	  });
	  store.load(); //加载数据
	  //创建下拉列表框
	  var chkEdu = new Ext.form.ComboBox({
	  name: "chkEdu",
	  fieldLabel: "最高学历",
	  store: store,
	  mode: "local",
	  triggerAction: "all",
	  emptyText: "请选择最高学历",
	  displayField: "ename", //显示值
	  valueField: "eid", //实际值,
	  value: 3 //缺省值
	  });
	  //最喜欢的数字
	  var numLove = new Ext.form.NumberField({
	  name: "numLove",
	  fieldLabel: "最喜欢的数字"
	  });
	  //家庭住址
	  var areaAddress = new Ext.form.TextArea({
	  name: "areaAddress",
	  fieldLabel: "家庭住址",
	  width: 500,
	  height: 50
	  });
	  //上班时间
	  var timeWork = new Ext.form.TimeField({
	  name: "timeWork",
	  fieldLabel: "上班时间",
	  increment: 30,
	  format: "H:i"
	  });
	  /*var timeWork = {
	  xtype: "timefield",
	  name: "timeWork",
	  fieldLabel: "上班时间"
	  };*/
	  //个人简介
	  var htmlIntro = new Ext.form.HtmlEditor({
	  name : "htmlIntro",
	  fieldLabel: "个人简介",
	  enableLists: false,
	  enableSourceEdit: false,
	  height: 150
	  });
	//照片
	var txtPhoto = new Ext.form.TextField({
	name: "txtPhoto",
	inputType: "file",
	fieldLabel: "照片",
	width: 500
	});
	//提交按钮
	var btnSubmit = new Ext.Button({
	text: "提交",
	handler: function(){
	f.getForm().submit({
	success: function(form, action){
	form.items.each(function(field){
	if(field.isFormField){
	alert(field.getName() + "=" + field.getValue());
	}
	});
	},
	failure: function(){
	Ext.MessageBox.alert("", "对不起，表单提交失败！");
	}
	});
	}
	});
	//重置按钮
	var btnReset = new Ext.Button({
	text: "重置" ,
	handler: function(){
	f.getForm().reset();
	}
	});
	
	var f = new Ext.form.FormPanel({
	url: "../FormServlet",
	method: "post",
	renderTo: "a",
	title: "新增员工",
	style: "padding: 10px",
	frame: true,
	labelAlign: "right",
	width: 650,
	autoHeight: true,
	items:[txtName, txtPassword, grpSex, dateBirthday, grpGobby, chkEdu, numLove,
	areaAddress, timeWork, htmlIntro, txtPhoto],
	buttons:[btnSubmit, btnReset]
	});
}