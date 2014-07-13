<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.pandorabox.domain.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
					+ contextPath + "/";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ransanity</title>
<meta property="wb:webmaster" content="d1eb041d8f189029" />
<meta name="keywords" content="艺术、交互设计、动漫、历史、怀旧、文化、体育、工业、建筑设计、时代、旅行、新奇、创意" />
<meta name="description" content="燃悦读为你提供侵入灵魂的阅读体验。" />
<meta name="author" content="hywang@ransanity.com" />
<meta name="build" content="2013-4-30" />
<meta name="copyright" content="Ransanity" />
<meta name="robots" content="all" />
<link rel="shortcut icon" type="image/ico" href="/favicon.ico" />
<!-- <link href="css/layout.css" rel="stylesheet" type="text/css" /> -->
<link href="<c:url value="/css/layout.css" />" rel="stylesheet">
<link href="<c:url value="/css/vertical.css" />" rel="stylesheet">
<!-- <link href="css/vertical.css" rel="stylesheet" type="text/css" /> -->
<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>
<!--KindEditor-->
<script src="<c:url value="/js/kindeditor-all-min.js" />"></script>
<script charset="utf-8" src="<c:url value="/js/lang/zh_CN.js" />"></script>
<script src="<c:url value="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=90749187" />" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/js/pandora.js" />"></script>
</head>

<body>
<script>
$(document).ready(function () {
	applicationParams.applicationContext = "<%=contextPath%>";
	applicationParams.basePath = "<%=basePath%>";
});
</script>
<%JSONObject articleData = (JSONObject)request.getAttribute("article");
int id=-1;
if(articleData!=null){ 
	id = articleData.getInt("articleId");
} 
if(id>=0){
%>
<script>
$(document).ready(function () {
 	createArticle(<%=articleData%>);
 	showCurrentArticle();
});
</script>
<% } else{%>
<script>
$(document).ready(function () {
	ajaxLoad(true);
});
</script>
<%} %>
<script>

	//自定义分享内容时的APPKEY
	var jiathis_config = {
		appkey : {
			"tsina" : "90749187"
		}
	};
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1397804146285506" charset="utf-8"></script>
<!-- JiaThis Button END -->
<div class="bar" id="navigator">
  <div class="body"><SCRIPT LANGUAGE="JavaScript">
      <!--
          
      //-->
      </SCRIPT>
    <div class="header">
      <h2>Ransanity</h2>
      <a href="#" class="classify"><strong>艺术</strong><em>▼</em><i style="display: none">▲</i></a>
      <div class="icon"><a href="#" class="cur">1</a><a href="#">2</a><a href="#">3</a></div>
      <a href="#" class="addArticle"><s>+</s>创造一页内容</a> </div>
    <div class="selectbox">
      <div class="body">
        <div class="item"><a href="#">全部</a><a href="#">艺术</a><a href="#">交互设计</a><a href="#">动漫</a><a href="#">历史</a><a href="#">怀旧</a><a href="#">文化</a><a href="#">体育</a><a href="#">工业</a><a href="#">建筑设计</a><a href="#">时代</a><a href="#">旅行</a><a href="#">新奇</a><a href="#">创意</a></div>
        <div class="my"> <a href="#">我的收藏</a> <a href="#">我创建的</a> </div>
      </div>
    </div>
  </div>
</div>

<!--这是所有文章列表-->
<ul class="article_container"></ul>
<div class="bottombar cl"> 
	<span> 
		<a href="#" class="icon like likeon">喜欢</a> 
		<a href="#" class="icon music">音乐</a> </span> 
				<cite>
					<a href="#" class="icon edit">编辑</a>
					<a href="#" class="icon del">× 删除</a>By wbrover
				</cite>
		<a href="#" class="icon talk">讨论</a> <a href="#" class="icon share">分享</a> 
<!-- JiaThis Button BEGIN -->
	<div class="jiathis_style_24x24">
		<a class="jiathis_button_tsina"></a>
		<a class="jiathis_button_tqq"></a>
		<a class="jiathis_button_weixin"></a>
<!-- 		<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a> -->
</div>
<!-- JiaThis Button END -->
	</div>
<div class="cpbottom cl"> <a href="#" class="cancel">取消</a> <a href="#" class="submit">确定</a> </div>
<div class="setting">页面设置<em>▼</em><i style="display: none">▲</i></div>


<!--KeEditor Element-->
<textarea class="keditor" name="content"></textarea>
<input class="edit_title" type="text" name="title" hidden="true"/>
<div class="cp">
  <dl class="layout">
    <dt>页面布局</dt>
    <dd>
      <ul class="cl">
<!--         <li class="left"><a href="#"><cite>文字居左</cite></a>文字居左</li> -->
					
		<li class = "left"><input name="layout" type="radio" size="10"
					value="horizontal" checked="checked" />文字居右</li>
<!--         <li class="align"><a href="#"><cite>文字居中</cite></a>文字居中</li> -->
        			
        <li class="align"><input name="layout" type="radio" size="10" 
        			value="center" />文字居中</li>
      </ul>
    </dd>
  </dl>
  <dl class="bg">
 	 <input type="file" name="file" class="addImage" multiple />
    <dt><button class="uploadImgBtn" ></button></dt>
    <dd>
      <ul class="cl">
      </ul>
    </dd>
  </dl>
  <dl class="music">
  	<input type="file" name="file" class="addMusic">
     <dt><button class="uploadMusicBtn" ></button></dt>
		<dd>
			<ul class="cl">
			</ul>
		</dd>
  </dl>
</div>

<!--负责提交文章的修改/新增的表单-->
<form method="post" enctype="multipart/form-data" id="dataForm">
		<input type="submit" value="submit" class="hidden_submit" />
</form>
<div class="confirm">
  <dl>
    <dt>确认删除本页?</dt>
    <dd>页面删除后将无法恢复</dd>
  </dl>
  <div class="footer">
    <button type="button" class="pn pnr">删除</button>
    <button type="button" class="pn pnc">取消</button>
  </div>
</div>
</body>
</html>