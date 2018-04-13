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
 <script type="text/javascript">
 	function doforward(){
 		window.location.href = '<%=request.getContextPath() %>/feedback/toFeedback';
 	}
 </script>
</head>
<body class="bg_efeff4" scroll=no>
<div class="">
	<div id="main_body">
	<h2 id="changjian"><span style="color:#8e8e93;">常见问题罗列</span></h2>
	<div id="faq_section" class="div2">
	  <ul id="hotfaq_14" class="list1" style="">
	  <c:forEach items="${list}" var="help" varStatus="index">
	  	<li><span style="font-weight: 2px;">${index.index+1}:${help.question }</span>
	  		<br /><div style="padding: 10px 0 0px 20px;">${help.answer}</div>
	  	</li>		
	  </c:forEach>
	  	
		</ul>
	</div>
	<div class="div3"><img alt="" src="<%=request.getContextPath() %>/static/image/4.png" onclick="doforward()" width="40%"></br>&nbsp;</div>
	</div>
</div>
</body>
</html>