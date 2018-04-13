<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>用户查询</title>
     <link href="easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="easyui/js/json2.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.edatagrid.js"></script>
 
 <script type="text/javascript">
		$( 
			function(){
				var datagridInstance; //定义全局变量datagrid
	            var editIndex = undefined; //定义全局变量：当前编辑的行		
					
	            datagridInstance = $('#userList').datagrid({
					url:'user/findUser',
					method: 'get',
					title: '用户查询',
					iconCls: 'icon-save',
					fitColumns: true,
					idField: 'id',
					//singleSelect: true,
					
					pagination:true,
					columns:[[
						{field:'id', title: '编号', width:80},
						{field:'userName',title:'用户名',width:80},
						{field:'nickName',title:'昵称',width:80},
						{field:'phone',title:'电话号码',width:120},
						{field:'sex',title:'性别',width:80,
							formatter: function(value,row,index){
												if (value == 0){
													return "男";
												} else if (value == 1){
													return "女";
												}
											}
						},
						{field:'age',title:'年龄',width:80},
						{field:'status',title:'用户是否在线',width:80,
							formatter: function(value,row,index){
								if (value == 0){
									return "是";
								} else if (value == 1){
									return "否";
								}
							}
						},
						{field:'ip',title:'登陆ip',width:180},
						{field:'lastLoginTime',title:'最后登陆时间',width:120},
					]],
					toolbar:'#searchTabId'
				});
		}
		);
		function queryUser() {
			var phone = $("#phone").val();
			var deviceType = $("#type").combobox("getValue");
			var status = $("#status").combobox("getValue");
			$('#userList').datagrid('load',
				{
					phone:phone,
					status:status,
					type:deviceType
				});
		};
		function cleanQueryUser() {
			$("#phone").val("");
			$("#type").combobox("clear");
			$("#status").combobox("clear");
		};
	</script>
</head>

<body >

<div  id="userList">
<div id="searchTabId" style="padding:3px">
	<span>电话号码:</span>
	<input id="phone" style="line-height:26px;border:1px solid #ccc">
	<span>设备类型:</span>
	<input id="type" class="easyui-combobox" data-options="
		valueField: 'value',
		textField: 'text',
		panelHeight:75,
		data: [{
			text: '手机用户',
			value: '1'
		},{
			text: '手表用户',
			value: '2'
		},{
			text: '无设备用户',
			value: '3'
		}]" />

	<span>用户是否在线:</span>
	<input id="status" class="easyui-combobox" data-options="
		valueField: 'value',
		textField: 'text',
		panelHeight:50,
		data: [{
			text: '在线用户',
			value: '0'
		},{
			text: '不在线用户',
			value: '1'
		}]" />
	<a href="javaScript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onclick="queryUser()">Search</a>
	<a href="javaScript:void(0)" class="easyui-linkbutton"   onclick="cleanQueryUser()">Clean</a>
</div>

</div>
</body>
</html>
