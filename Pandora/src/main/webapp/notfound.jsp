<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>404</title>
  <style type="text/css">
    BODY {font-size: 20px; font-weight: 700; color: black; margin-bottom: 20px;}
  </style>
  <link rel="shortcut icon" type="image/ico" href="<%=basePath%>/images/ran.ico" />
 </head>
  
  <body>
    找不到指定资源. <br>
  </body>
</html>
