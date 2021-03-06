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
<html xmlns:wb="http://open.weibo.com/wb">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>燃悦读(Ransanity)</title>
<meta property="wb:webmaster" content="d1eb041d8f189029" />
<meta name="keywords" content="燃悦读希望可以通过创新的内容组织方式，为你提供沁入式的阅读体验，燃悦读可以用来分享知识和想法，可以用来抒发情感和寻找同类。" />
<meta name="description" content="燃悦读为你提供侵入灵魂的阅读体验。" />
<meta name="author" content="hywang@ransanity.com" />
<meta name="build" content="2013-4-30" />
<meta name="copyright" content="Ransanity" />
<meta name="robots" content="all" />
<link rel="shortcut icon" type="image/ico" href="<%=basePath%>/images/ran.ico" />
<link href="<%=basePath%>/css/vertical.css" rel="stylesheet" />
<link href="<%=basePath%>/css/layout.css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript"></script>

<!-- qtip -->
<script type="text/javascript" src="<%=basePath%>/js/jquery.qtip.min.js"></script>
<!-- KindEditor -->
<script src="<%=basePath%>/js/kindeditor-all-min.js"></script>
<script charset="utf-8" src="<%=basePath%>/js/lang/zh_CN.js"/></script>
<!-- Weibo -->
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=90749187" type="text/javascript" charset="utf-8"></script>
<!-- Ransanity -->
<script type="text/javascript" src="<%=basePath%>/js/ran.js" /></script>
<script type="text/javascript" src="<%=basePath%>/js/mustache.js" /></script>
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
	ajaxLoad();
});
</script>
<%} %>
<script>

	//自定义分享内容时的APPKEY
	var jiathis_config = {
		appkey : {
			"tsina" : "90749187"
		},
	};
</script>

<!--文章模板-->
<script id="article-template" type="x-tmpl-mustache">
<li>
    <div class="article" aid={{articleId}}>
		<div class="wrapbg">
			{{#wrapbg}}
				<img src={{url}} imageid={{imageId}} />
			{{/wrapbg}}
		</div>
		<h1 class="shown_title">{{title}}</h1>
		<div class="inner">{{{text}}}</div>
		<ul class="ownedImgs" style="display:none;">
			{{#images}}
				<li descriptorid={{imageId}} url={{url}}>
					<img src={{snapshotUrl}} />
					<a href="#" class="delImg">删除</a>
				</li>
			{{/images}}
		</ul>
		
		<ul class="ownedMusics" style="display:none;">
			{{#files}}
				<li descriptorid={{fileId}} url={{url}}>{{name}}
					<a href="#" class="delMsc">删除</a>
					{{#selected}}
						<cite class="pickedMusic">
							<img src="<%=basePath%>/images/play16.ico" />
						</cite>
					{{/selected}}
				</li>
			{{/files}}
		</ul>
		<div class="author" style="display:none;">By <a href={{author.url}} uid={{author.userId}}>{{author.username}}</a></div>
	</div>
</li>
</script>
<div class="bar" id="navigator">
  <div class="body"><SCRIPT LANGUAGE="JavaScript">
      <!--
          
      //-->
      </SCRIPT>
    <div class="header">
      <h2>Ransanity</h2>
      <a href="#" class="classify"><strong>全部</strong><em>▼</em><i style="display: none">▲</i></a>
      <a href="#" class="addArticle"><s>+</s>写一篇</a> </div>
    <div class="selectbox">
      <div class="body">
        <div class="item"><a href="#">全部</a><a href="#">艺术</a><a href="#">交互设计</a><a href="#">动漫</a><a href="#">历史</a><a href="#">怀旧</a><a href="#">文化</a><a href="#">体育</a><a href="#">工业</a><a href="#">建筑设计</a><a href="#">时代</a><a href="#">旅行</a><a href="#">新奇</a><a href="#">创意</a></div>
        <div class="my"> <a href="#">我的收藏</a> <a href="#">我创建的</a> </div>
      </div>
       <div id="wb_connect_btn" ></div>
    </div>
  </div>
</div>

<a class ="step previous" href="#"></a>
<a class ="step next" href="#"></a>

<!--这是所有文章列表-->
<ul class="article_container"></ul>
<div class="bottombar cl"> 
	<span> 
		<a href="#" class="icon like">喜欢</a> 
		<a href="#" class="icon music_on">音乐</a> 
	</span> 
	<cite class="controlPanel">
	</cite>
<!-- 	<a href="#" class="icon comment">讨论</a>  -->
	<a href="#" class="icon share">分享</a> 
<!-- JiaThis Button BEGIN -->
	<div class="jiathis_style_32x32" >
		<a class="jiathis_button_tsina"></a>
		<a class="jiathis_button_tqq"></a>
		<a class="jiathis_button_weixin"></a>
	</div>
	<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1397804146285506" charset="utf-8"></script>
	<!-- JiaThis Button END -->
</div>
<div class="cpbottom cl"> <a href="#" class="cancel">取消</a> <a href="#" class="submit">确定</a> </div>
<div class="setting">页面设置<em>▼</em><i style="display: none">▲</i></div>


<!--KeEditor Element-->
<textarea class="keditor" name="content" placeholder = "文章正文"></textarea>
<input class="edit_title" type="text" name="title" hidden="true" placeholder="文章标题"/>
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