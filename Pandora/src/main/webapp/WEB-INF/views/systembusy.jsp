<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>500 System is busy</title>
  <style type="text/css">
    BODY p{font-size: 20px; font-weight: 700; color: black; margin-bottom: 20px;}
  </style>
  <link rel="shortcut icon" type="image/ico" href="<%=basePath%>/images/ran.ico" />
 </head>
 <body>
   <p>
    	系统繁忙，请稍后重试.
   </p>
 </body>

</html>
