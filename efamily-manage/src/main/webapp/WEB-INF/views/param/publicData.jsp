<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
<title>公共参数设置</title>
     <link href="easyui/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="easyui/js/json2.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.edatagrid.js"></script>
 
 <script type="text/javascript">
	 	function _endEditing(target,editIndex){
			if (editIndex == undefined){return true}
			if (target.datagrid('validateRow', editIndex)){
				target.datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		};
		$( 
			function(){
				var datagridInstance; //定义全局变量datagrid
	            var editIndex = undefined; //定义全局变量：当前编辑的行		
					
	            datagridInstance = $('#publicDataList').datagrid({
					url:'publicData/findPublicData',
					method: 'get',
					title: 'Public Data',
					iconCls: 'icon-save',
					fitColumns: true,
					//idField: 'id',
					//singleSelect: true,
					
					pagination:true,
					columns:[[
						{field:'id', title: '编号', width:80, checkbox: true },
						{field:'updateFlag',title:'Update Flag',width:80,editor: { type: 'numberbox', options: { required: true}}},
						{field:'deviceType',title:'Device Type',width:80,editor: { type: 'numberbox', options: { required: true}}},
						{field:'versionNumber',title:'Version Number',width:80,editor: { type: 'validatebox', options: { required: true}}},
						{field:'versionDescribe',title:'Version Describe',width:80,editor: { type: 'validatebox', options: { required: true}}},
						{field:'downloadUrl',title:'Download Url',width:180,editor: { type: 'validatebox', options: { required: true}}},
						{field:'logoUrl',title:'Logo Url',width:120,align:'right',editor: { type: 'validatebox', options: { required: true}}},
						{field:'weChat',title:'We Chat',width:80,align:'right',editor: { type: 'validatebox', options: { required: true}}},
						{field:'weiboName',title:'Weibo Name',width:80,editor: { type: 'validatebox', options: { required: true}}},
						{field:'weiboUrl',title:'Weibo Url',width:160,align:'center',editor: { type: 'validatebox', options: { required: true}}},
						{field:'phoneNumber',title:'Phone Number',width:80,align:'center',editor: { type: 'validatebox', options: { required: true}}}
					]],
					toolbar: [{
					    text: 'Add',
					    iconCls: 'icon-add',
					    handler: function () { 
					    	if(!_endEditing(datagridInstance,editIndex)) {
					    		datagridInstance.datagrid('selectRow', editIndex);
					    		return;
					    	}
					    	var newRow = datagridInstance.datagrid('appendRow',
					    			{}
					    	);
					    	var rows = datagridInstance.datagrid('getRows');
					    	datagridInstance.datagrid("beginEdit",rows.length-1);
	                    	editIndex = rows.length-1;
					    }
					}, {
					    text: 'Edit',
					    iconCls: 'icon-edit',
					    handler: function () { 
					    	//修改时要获取选择到的行
		                     var rows = datagridInstance.datagrid("getSelections");
		                     //如果只选择了一行则可以进行修改，否则不操作
		                     if (rows.length == 1) {
		                    	 var index = datagridInstance.datagrid("getRowIndex", rows[0]);
		                    	 if(index != editIndex) {
		                    		 if(!_endEditing(datagridInstance,editIndex)) {
		 					    		datagridInstance.datagrid('selectRow', editIndex);
		 					    		return;
		 					    	}
		                    	 }
		                    	 datagridInstance.datagrid("beginEdit",index);
		                    	 editIndex = index;
		                     }else {
		                    	 $.messager.alert('提示','请选择一行','info');
		                     }
					    }
					}, '-', {
					    text: 'Save',
					    iconCls: 'icon-save',
					    handler: function () {
					    	if(!_endEditing(datagridInstance,editIndex)) {
 					    		datagridInstance.datagrid('selectRow', editIndex);
 					    		return;
 					    	}
					    	debugger;
					    	var selectRows = datagridInstance.datagrid('getChecked'); 
					    	var updateRows = datagridInstance.datagrid('getChanges','updated');
					    	var insertRows = datagridInstance.datagrid('getChanges','inserted');
					    	var updateData = [];
					    	$.each(selectRows,function(i,selectRow) {
					    		$.each(updateRows,function(j,updateRow) {
					    			if(datagridInstance.datagrid("getRowIndex", updateRow) ==
					    				datagridInstance.datagrid("getRowIndex", selectRow)) {
					    				updateData.push(updateRow);
					    			}
					    		})
					    		$.each(insertRows,function(j,insertRow) {
					    			if(datagridInstance.datagrid("getRowIndex", insertRow) ==
					    				datagridInstance.datagrid("getRowIndex", selectRow)) {
					    				updateData.push(insertRow);
					    			}
					    		})
					    	})
					    	debugger;
					    	if(updateData.length == 0) {
					    		$.messager.alert("提示", "请选择需要更新的行", "warning");
					    	}else {
					    		var updateJsonData = JSON.stringify(updateData);
					    		$.ajax({
					    				url:'publicData/updatePublicData',
					    				type:'POST',
					    				contentType : 'application/json;charset=utf-8', //设置请求头信息
					    				data:updateJsonData,
					    				dataType:"json",
					    				success:function(data) {
					    						$.messager.alert("提示", "数据更新成功", "info");
					    				}
					    		})
					    	}		
					    	
					    }
					},'-', {
					    text: 'Del',
					    iconCls: 'icon-remove',
					    handler: function () {
		                    //删除时先获取选择行
		                     var rows = datagridInstance.datagrid("getSelections");
		                     //选择要删除的行
		                     if (rows.length > 0) {
		                         $.messager.confirm("提示", "你确定要删除吗?", function (r) {
		                             if (r) {
		                                 var ids = [];
		                                 for (var i = 0; i < rows.length; i++) {
		                                     ids.push(rows[i].id);
		                                 }
		                                 //将选择到的行存入数组并用,分隔转换成字符串，
		                                 //本例只是前台操作没有与数据库进行交互所以此处只是弹出要传入后台的id
		                                 //alert(ids.join(','));
		                                 $.ajax({
							    				url:'publicData/delPublicData',
							    				type:'POST',
							    				contentType : 'application/json;charset=utf-8', //设置请求头信息
							    				data:JSON.stringify(ids),
							    				dataType:"json",
							    				success:function(data) {
							    						$.each(rows,function(j,row) {
							    							var delIndex = datagridInstance.datagrid("getRowIndex", row);
							    							datagridInstance.datagrid('cancelEdit',delIndex).datagrid("deleteRow",delIndex);
							    						})
							    						
							    						$.messager.alert("提示", "数据删除成功", "info");
							    						
							    				}
							    		})
		                             }
		                         });
		                     }
		                     else {
		                         $.messager.alert("提示", "请选择要删除的行", "warning");
		                     }
					    }
					},
					],
					onLoad:function() {
						alert("load success");
						$("#searchTabId").appendTo(".datagrid-toolbar");
					}
				    
				});
		}
		);
		$("#searchTabId").appendTo(".datagrid-toolbar");
	</script>
</head>

<body >

<div  id="publicDataList">
<div id="searchTabId" style="padding:3px">
	<span>Item ID:</span>
	<input id="itemid" style="line-height:26px;border:1px solid #ccc">
	<span>Product ID:</span>
	<input id="productid" style="line-height:26px;border:1px solid #ccc">
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
</div>

</div>
</body>
</html>
