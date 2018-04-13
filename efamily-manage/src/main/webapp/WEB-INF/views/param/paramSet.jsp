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
<title>参数设置</title> 
 <link rel="stylesheet" rev="stylesheet" href="<%=request.getContextPath() %>/static/css/help.css" type="text/css" media="screen"/>
 <script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/jquery.tmpl.min.js"></script>
</head>
<body scroll=no>
<div class="">
	<div id="main_body">
		<form action="/paramSet/setParam" method="post" id="form">
		<div style="float: left;margin-right: auto;margin-left: 25%;">
	  	<ul id="hotfaq_14" class="" style="">
	 		<li>
	 		连网心跳频率：<input id="connect_beat" name="connect_beat" value="${connect_beat}">
	 		</li>
	 		<li>
	 		连网重试次数：<input id="net_retry" name="net_retry" value="${net_retry}">
	 		</li>
	 		<li>
	 		最大亮度：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="bright_max" name="bright_max" value="${bright_max}">
	 		</li>
	 		<li>
	 		最大声音：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="sound_max" name="sound_max" value="${sound_max}">
	 		</li>
	 		<li>
	 		最大信号值：&nbsp;&nbsp;&nbsp;&nbsp;<input id="signal_max" name="signal_max" value="${signal_max}">
	 		</li>
	 		<li>
	 		信号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="signal" name="signal" value="${signal}">
	 		</li>
	 		<li>
	 		电池上传频率：<input id="battery_upload_freq" name="battery_upload_freq" value="${battery_upload_freq}">
	 		</li>
	 		<li>
	 		定位采集频率：<input id="location_gather_freq" name="location_gather_freq" value="${location_gather_freq}">
	 		</li>
	 		<li>
	 		心率开关：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="health_heart_onff" name="health_heart_onff" value="${health_heart_onff}">
	 		</li>
	 		<li>
	 		心率上传频率：<input id="health_heart_upload_freq" name="health_heart_upload_freq" value="${health_heart_upload_freq}">
	 		</li>
	 		
	 		<li>
	 		计步上传频率：<input id="health_walk_upload_freq" name="health_walk_upload_freq" value="${health_walk_upload_freq}">
	 		</li>
	 		<li>
	 		静音模式：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="silent_onff" name="silent_onff" value="${silent_onff}">
	 		</li>
		</ul>
		</div>
		<div style="float: left;">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div style="float: left;">
	  	<ul id="hotfaq_14" class="" style="">
	 		
	 		<li>
	 		连网超时：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="connect_timeout" name="connect_timeout" value="${connect_timeout}">
	 		</li>
	 		<li>
	 		网络重启次数：<input id="net_repeat" name="net_repeat" value="${net_repeat}">
	 		</li>
	 		<li>
	 		亮度：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="bright" name="bright" value="${bright}">
	 		</li>
	 		<li>
	 		声音：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="sound" name="sound" value="${sound}">
	 		</li>
	 		<li>
	 		信号上传频率：<input id="signal_upload_freq" name="signal_upload_freq" value="${signal_upload_freq}">
	 		</li>
	 		<li>
	 		最大电池值：&nbsp;&nbsp;&nbsp;&nbsp;<input id="battery_max" name="battery_max" value="${battery_max}">
	 		</li>
	 		<li>
	 		定位开关：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="location_onff" name="location_onff" value="${location_onff}">
	 		</li>
	 		<li>
	 		定位上传频率：<input id="location_upload_freq" name="location_upload_freq" value="${location_upload_freq}">
	 		</li>
	 		<li>
	 		心率采集频率：<input id="health_heart_gather_freq" name="health_heart_gather_freq" value="${health_heart_gather_freq}">
	 		</li>
	 		<li>
	 		计步开关：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="health_walk_onff" name="health_walk_onff" value="${health_walk_onff}">
	 		</li>
	 		<li>
	 		振动模式：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="vibrate_onff" name="vibrate_onff" value="${vibrate_onff}">
	 		</li>

		</ul>
		</div>
		</form>
		<div class="div4"><img alt="" src="<%=request.getContextPath() %>/static/image/3.png" width="14%" height="auto" id="button" onclick="submit()"></img><div class="div4">&nbsp;&nbsp;</div></div>
	</div>
</div>
 <script type="text/javascript">
 	function submit(){
 		$('#form').submit();
 	}
 </script>
</body>
</html>