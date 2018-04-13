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
<style>
body{padding:0;margin:0;font:normal 12px/24px "\5FAE\8F6F\96C5\9ED1";color:#444;}
table{width:100%;border:0;margin:10px auto 0;text-align:center;border-collapse:collapse;border-spacing:0;}
table th{background:#0090D7;font-weight:normal;line-height:30px;font-size:14px;color:#FFF;}
table tr:nth-child(odd){background:#F4F4F4;}
table td:nth-child(even){color:#C00;}
table tr:nth-child(5){background:#73B1E0;color:#FFF;}
table tr:hover{background:#73B1E0;color:#FFF;}
table td,table th{border:1px solid #EEE;}
</style>
<body>  
	<div class="main">
		<div class="title">设备统计</div>
		<div>
			<div> 
				<hr/>
				<table class="table" border="1">
					<tbody>
					  <tr>
					     <td colspan="6">客户设备统计表</td>			     					     					     					     
					  </tr>
					  <tr>
					     <td>客户</td>
					     <td>设备型号</td>
					     <td>已生产</td>	
					     <td>当前绑定设备</td>
					     <td>已解绑设备</td>
					     <td>已入库</td>				     					     					     					     
					  </tr>
				      <c:forEach var="sd" items="${statisticsDataList}">
						<tr >
							<td > ${sd.customerName} </td>
							<td > ${sd.model} </td>
							<td > ${sd.produceDeviceData} </td>
							<td > ${sd.bindDeviceData} </td>
							<td > ${sd.unbindDeviceData} </td>
							<td > ${sd.storageDeviceData} </td>
						</tr>
				      </c:forEach>
				      <tr >
							<td > 合计： </td>
							<td >  </td>
							<td > ${produceDeviceDataTotal} </td>
							<td > ${bindDeviceDataTotal} </td>
							<td > ${unbindDeviceDataTotal} </td>
							<td > ${storageDeviceDataTotal} </td>
					  </tr>
					</tbody>
				</table>
				
			</div>
			<hr/>
	</div> 
	</div>

</body>

</html>