<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>参数设置</title> 
 <link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/static/css/help.css" type="text/css" media="screen"/>
 <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
 <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
 <script type="text/javascript" src="../../jquery.min.js"></script>
 <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
 <script type="text/javascript">
 	function submit(){
 		$('#form').submit();
 	}
 </script>
</head>

<body scroll=no>
<form action="/paramSet/setParam" method="post" id="form">
<div style="float: left;margin-right: auto;margin-left: 25%;">
	  	<ul id="hotfaq_14" class="" style="">
	 		<li>
	 		push命令:

	 		<select class="easyui-combobox" name="language">
	    					<option value="110001">获取公共数据</option>
	    					<option value="20100">设备软件查询</option>
	    					<option value="20109">设备移动信息查询</option>
	    					<option value="20142">设备自动开关机查询</option>
	    					<option value="20152">设备时间查询</option>
	    					<option value="20163">最大亮度查询</option>
	    					<option value="20204">参数查询</option>
	    					<option value="20205">用户信息查询</option>
	    					<option value="20196">电池数据查询</option>
	    					<option value="20402">提醒设置查询</option>
	    					<option value="20825">设备计步查询</option>
	    					<option value="20195">电池最大电量查询</option>
	    					<option value="28962">设备监控上传</option>
	    					<option value="20185">设备信号最大值查询</option>
	    					<option value="20186">设备信号数据查询</option>
	    					<option value="20321">设备通讯录查询</option>
	    					<option value="20502">设备聊天设置查询</option>
	    					<option value="20624">设备定位星信噪比查询</option>
	    					<option value="20625">设备定位查询</option>
	    					<option value="20647">设备定位wifi查询</option>
	    					<option value="20725">设备心率查询</option>
	    					<option value="20925">设备睡眠查询</option>
	    					<option value="29104">设备短信开关机查询</option>
	    					<option value="29132">设备短信定位开关查询</option>
	    					<option value="29133">设备短信定位数据集查询</option>
	    	</select>
	 		</li>
	 		</ul>
</div>
</from>
<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/3.png" width="14%" height="auto" id="button" onclick="submit()"></img><div class="div4">&nbsp;&nbsp;</div></div>
</body>
</html>
