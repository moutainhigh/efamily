<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>using commons Upload to upload file </title>
</head>
<style>
* { font-family: "宋体"; font-size: 14px }
</style>
<body>
<p align="center"> 请您选择需要上传的文件</p>
<form id="form1" name="form1" method="post" action="upload" enctype="multipart/form-data">
 <table border="0" align="center">
  <tr>
   <td>type：</td>
   <td>
    <input name="type" type="text" id="type" value="image" size="20" ></td>
  </tr>  
    <tr>
   <td>mediaType：</td>
   <td>
    <input name="mediaType" type="text" id="mediaType" value="jpg" size="20" ></td>
  </tr> 
    <tr>
   <td>isMulti：</td>
   <td>
    <input name="isMulti" type="text" id="isMulti" value="1" size="20" ></td>
  </tr> 
  <tr>
   <td>上传文件：</td>
   <td><input name="file" type="file" size="20" ></td>
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