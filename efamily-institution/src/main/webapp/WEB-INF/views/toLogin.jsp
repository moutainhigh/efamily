<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>using commons Upload to upload file </title>
</head>
<style>
* { font-family: "宋体"; font-size: 14px }
</style>
<body>
<p align="center"> 登录页面</p>
<form id="form1" name="form1" method="post" action="login" enctype="multipart/form-data">
 <table border="0" align="center">
  <tr>
   <td>phoneNumber：</td>
   <td>
    <input name="phoneNumber" type="text" id="phoneNumber" value="" size="20" ></td>
  </tr>  
  <tr>
   <td>password：</td>
   <td>
    <input name="password" type="text" id="password" value="" size="20" ></td>
  </tr>  
  <tr>   
   <td></td><td>
    <input type="submit" name="submit" value="提交" >
    <input type="reset" name="reset" value="重置" >
   </td>
  </tr>
 </table>
</form>
</body>
</html>