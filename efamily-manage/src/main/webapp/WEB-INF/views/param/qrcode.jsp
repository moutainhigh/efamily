<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
<meta charset="utf-8" />
<base href="<%=basePath %>">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>imei添加</title> 
 <link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/static/css/help.css" type="text/css" media="screen"/>
    <!-- <link href="easyui/css/default.css" rel="stylesheet" type="text/css" /> -->
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <script type="text/javascript" src="easyui/js/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
 <script type="text/javascript">
 	function submit(){
 		if($('#form').form('validate')) {
 			$('#form').submit();
 		}
 		
 	};
 	
 </script>
</head>

<body scroll=no>
<c:if test="${not empty success}">  
	<script type="text/javascript">
 		alert("imei添加成功!");
 	</script>
</c:if>
<div class="">
	<div id="main_body">
		<form action="qrcode/addQrcodeByFile" enctype="multipart/form-data" method="post" id="form">
			<div style="float: left;margin-right: auto;margin-left: 25%;">
				<ul id="hotfaq_14" class="" style="">
			 		<li>
			 		imei文件：<input class="easyui-validatebox" data-options="required:true" type="file" name="file" />
			 		</li>
			 	</ul>
			</div>
		</form>
		<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/3.png" width="14%" height="auto" id="button" onclick="submit()"></img><div class="div4">&nbsp;&nbsp;</div></div>
	</div>
</div>
</body>
</html>
