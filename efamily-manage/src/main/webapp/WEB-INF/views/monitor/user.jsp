<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/static/css/style.css" />
<title>监控</title>
</head>
<body>  
	<div class="main">
		<div class="title">用户统计</div>
		<div>
			<div> 
				<hr/>
				<table class="table">
					<tbody>
						<tr class="summary">
							<td class="tb_name">用户数：    ${userMonitor.totalCount}</td>
						</tr>
						<tr>
							<td class="tb_name">APP：      ${userMonitor.totalAppCount}</td>
						</tr>
						<tr>
							<td class="tb_name">WATCH：  ${userMonitor.totalWatchCount}</td>
						</tr>
					</tbody>
				</table> 
				<hr/>
				<c:forEach var="ud" items="${userMonitor.userMonitorStrucDetail}">
				<table class="table">
					<tbody>
						<tr class="summary">
							<td class="tb_name">当前在线：    ${ud.totalOnlineCount}           服务器ip：${ud.ip}       记录时间：${ud.time}</td>
						</tr>
						<tr>
							<td class="tb_name">APP：    ${ud.totalOnlineAppCount}</td>
						</tr>
						<tr>
							<td class="tb_name">WATCH：    ${ud.totalOnlineWatchCount}</td>
						</tr>
					</tbody>
				</table>
				</c:forEach>
				
				
			</div>
			<hr/>
<!-- 			<div> -->
<!-- 				<table class="table"> -->
<!-- 					<thead> -->
<!-- 						<tr class="tb_head"> -->
<!-- 							<th>用户昵称</th> -->
<!-- 							<th>性别</th> -->
<!-- 							<th>状态</th>  -->
<!-- 							<th>上次登入时间</th> -->
<!-- 							<th>上次登出时间</th> -->
<!-- 							<th class="tb_blank10"></th> -->
<!-- 						</tr> -->
<!-- 					</thead> -->
<!-- 					<tbody> -->
<%-- 						<c:forEach items="${userMonitor.userStrucList}" var="userStruc"> --%>
<!-- 							<tr class="tb_grid"> -->
<%-- 								<td>${userStruc.nickName}</td> --%>
<%-- 								<td><c:choose> --%>
<%-- 										<c:when test="${userStruc.sex==0}">男</c:when> --%>
<%-- 										<c:otherwise>女</c:otherwise> --%>
<%-- 									</c:choose> --%>
<!-- 								</td> -->
<%-- 								<td><c:choose> --%>
<%-- 										<c:when test="${userStruc.status==0}">在线</c:when> --%>
<%-- 										<c:otherwise>离线</c:otherwise> --%>
<%-- 									</c:choose> --%>
<!-- 								</td> -->
<%-- 								<td>${userStruc.lastLoginTime}</td> --%>
<%-- 								<td>${userStruc.lastLogoutTime}</td> --%>
<!-- 								<td class="tb_blank30"></td> -->
<!-- 							</tr> -->
<%-- 						</c:forEach>						 --%>
<!-- 					</tbody> -->
<!-- 				</table> -->
<!-- 			</div> -->
	</div> 
	</div>

</body>

</html>