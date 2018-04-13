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
<title>用户详情</title> 
 <link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/static/css/help.css" type="text/css" media="screen"/>
 <script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.tmpl.min.js"></script>
 
</head>
<body class="bg_efeff4">
<div class="pd-bottom" style="width: 100%;height: 100%">
	<div style="width: 100%;height: 100%">
	用户详情：：：：：：：：：：：
	 用户名称：${userBaseInfo.name} <br/>
	 用户健康：
	  <c:forEach var="item" items="${userHealthInfo.heartRateInfo}" varStatus="n">
        ${item.value}--${item.fromTime}--${item.toTime}--${item.flag}
      </c:forEach>
	 
	 用户定位：${userLocationInfo.location} <br/>	 
	</div>
</div>
</body>
</html>