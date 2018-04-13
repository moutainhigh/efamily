<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
<meta charset="utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>帮助与反馈</title> 
 <link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/static/css/help.css" type="text/css" media="screen"/>
 <script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.tmpl.min.js"></script>
 
</head>
<body class="bg_efeff4">
<div class="pd-bottom" style="width: 100%;height: 100%">
	<div style="width: 100%;height: 100%">
	<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/1.png" width="100%" height="auto"></div>
	<form action="<%=request.getContextPath() %>/feedback/addFeedback" method="post" id="form1">
		<input type="hidden" id="files" name="files"  value="">
		<div style="width:100%;height:220px;text-align:center;float:left; border: 1;border-bottom-color: #fff"><textarea id="content" name="content" style="width:99%;height: 100%;border: 0">请简要描写您的问题和建议，或留下您的联系方式，我们会尽快与您联系。</textarea></div>
		<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/2.png" width="100%" height="auto"></img></div>
		<div class="div4">&nbsp;&nbsp;</div>
		<div class="div4">&nbsp;&nbsp;</div>
		<div id="pictureDiv" class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/picture.png" height="120px"></img></div>
		
		<div class="div4">&nbsp;&nbsp;</div>
		<div class="div4">&nbsp;&nbsp;</div>
		<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/3.png" width="30%" height="auto" id="button"></img><div class="div4">&nbsp;&nbsp;</div></div>
	</form>
	<form id="J-form-upload-placeholder" name="form2" enctype="multipart/form-data" method="post" action="<%=request.getContextPath() %>/feedback/upload" style="display:inline;" target="J-iframe-placeholder">
							<input name="tempFilePlaceHolder" type="file" id="J-upload-file-placeholder" class="upload-file-button" size="40" hidefocus="true" value="导入"  style="display:none;"/>
							<input type="hidden" id="contentOld" name="contentOld" value="${content }">
							<input type="hidden" id="filesOld" name="filesOld" value="${files}">
							
					<iframe id="J-iframe-placeholder" name="J-iframe-placeholder" frameborder="0" style="display:none">
					</iframe>		
	</form>
	
 
	</div>
</div>
<script type="text/javascript">
var contentOld = $('#contentOld').val();
var baseUrl = '<%=request.getContextPath()%>';
if(contentOld!=null && contentOld !=''){
	$('#content').val(contentOld);
}
$('#content').click(function clean(){
	if($('#content').val()=='请简要描写您的问题和建议，或留下您的联系方式，我们会尽快与您联系。'){
		$('#content').val('');
	}
})
var bianji=document.getElementById("J-iframe-placeholder").contentWindow; 
bianji.document.write("<div id='fileDiv' >${files}</div>");
bianji.document.write("<div id='newFileDiv' >${files}</div>");
bianji.document.close(); 
var clickp = function (){
	document.getElementById('J-form-upload-placeholder').tempFilePlaceHolder.click();
};
function unbind(){
	$('#pictureDiv').find('img').unbind("click",clickp);
}
$('#pictureDiv').find('img').bind("click",clickp);

function changePic(){
	
	var newFile = document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("newFileDiv").innerHTML;
	if(newFile !=null && newFile !=''){
		unbind();
		$('#pictureDiv').find('img').attr("src",newFile);
		$('#pictureDiv').find('img').bind("click",clickp);
		return;
	}else{
		 setTimeout("changePic()", 1000);
	}
}



$('#J-upload-file-placeholder').bind('propertychange',function(){
	$('#contentOld').val($('#content').val());
	$('#filesOld').val(document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("fileDiv").innerHTML);
	document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("newFileDiv").innerHTML='';
	$(this).parent().submit();
	changePic();
}).bind('change',function(){
	$('#contentOld').val($('#content').val());
	$('#filesOld').val(document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("fileDiv").innerHTML);
	document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("newFileDiv").innerHTML='';
	$(this).parent().submit();
	changePic();
});
$('#button').click(function(){
	$('#files').val(document.getElementById("J-iframe-placeholder").contentWindow.document.getElementById("fileDiv").innerHTML);
	$('#form1').submit();
})
</script>
</body>
</html>