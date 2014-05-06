<%@page import="com.pandorabox.cons.CommonConstant"%>
<%@page import="com.pandorabox.service.upyun.UpYunFormRequest"%>
<%@page import="com.pandorabox.service.upyun.UpYunRequestBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">

<title>PandoraBox Create Page</title>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<link href="css/cp.css" rel="stylesheet" type="text/css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"
	type="text/javascript"></script>
<script src="js/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery.form.min.js" type="text/javascript"></script>

<%
	//初始化图片空间的提交参数
	String defaultDomain = CommonConstant.DEFAULT_UPYUN_DOMAIN;
	String imgBucket = "pandora001";
	String fileBucket = "pandora002";
	String commonSaveKey = "/{filename}{.suffix}";
	String imgFormApiSecret = "8N09dtHrGfab5NUS7eCzmySfrtQ=";
	String fileFormApiSecret = "80p8vGGFD6rFqYNic3amAVHTzrI=";
	//构造表单API请求
	UpYunFormRequest picBucketRequest = new UpYunRequestBuilder()
			.setFormApiSecret(imgFormApiSecret).setBucket(imgBucket)
			.setDomain(defaultDomain)
			.setExpiration(new Date().getTime() + 1800)
			.setSaveKey(commonSaveKey).buildFormRequest();
	UpYunFormRequest musicBucketRequest = new UpYunRequestBuilder()
			.setFormApiSecret(fileFormApiSecret).setBucket(fileBucket)
			.setDomain(defaultDomain)
			.setExpiration(new Date().getTime() + 1800)
			.setSaveKey(commonSaveKey).buildFormRequest();
%>
<script type="text/javascript">
	// prepare the form when the DOM is ready 
	$(document).ready(
			function() {
				// bind form using ajaxForm 
				var $articleForm = $("#articleForm");
				var $imageForm = $("#imageForm");
				var $musicForm = $("#musicForm");

				$("#submitForms").click(function() {
					$imageForm.submit();
				});

				$articleForm.ajaxForm({
					// dataType identifies the expected content type of the server response 
					//dataType:  'json', 

					// success identifies the function to invoke when the server response 
					// has been received 
					success : articleCommitted,
					error : articleError,
					complete : articleComplete
				});

				$imageForm.ajaxForm({
					// dataType identifies the expected content type of the server response 
					//dataType:  'json', 

					// success identifies the function to invoke when the server response 
					// has been received 
					success : imageCommitted,
					error : imageError,
					complete : imageComplete
				});

				$musicForm.ajaxForm({
					// dataType identifies the expected content type of the server response 
					//dataType:  'json', 

					// success identifies the function to invoke when the server response 
					// has been received 
					success : musicCommitted,
					error : musicError,
					complete : musicComplete
				});

				function articleCommitted(responseText, statusText, xhr, $form) {
					alert("article committed");
					$("#player").attr("src",
							"http://pandora002.b0.upaiyun.com/SPITZ.mp3");
					$("#player").attr("autoplay", "autoplay");
				}
				function articleError(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
					var exceptionInfo = JSON.parse(jqXHR.responseText);
					alert("code: "+exceptionInfo.code+"message: "+ exceptionInfo.message);
				}
				function articleComplete(jqXHR, textStatus) {
					console.log("article completed!");
				}
				function imageCommitted(responseText, statusText, xhr, $form) {
					$("#imgsToCommit").val(responseText);
					$musicForm.submit();
				}
				function imageError(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				}
				function imageComplete(jqXHR, textStatus) {
					console.log("image submit completed!");
				}
				function musicCommitted(responseText, statusText, xhr, $form) {
					$("#musicToCommit").val(responseText);
					$articleForm.submit();
				}
				function musicError(jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				}

				function musicComplete() {
					console.log("muisc submit completed!");
				}

				$(".hidden_submit").hide();
				
				
				var http_method = "PUT";
				var file;
				//提交到潘多拉服务器
				function submitToPandora(){
					$.ajax({
  						 type: "POST",
   						 url:"http://localhost:8886/PandoraBox/authorization",
						 headers:{
								"http_method_name": http_method,
								"content-length": file.size,
								"uri":"pandora001/wtf.jpg",
								"bucket":"pandora001"
								},		
   						 success: function(responseText, textStatus, jqXHR){
							  alert(responseText);
							  submitToUpYun(responseText);
						},
						error: function(XMLHttpRequest, textStatus, errorThrown){
							alert(textStatus);						
						}
					});
				}
				
				//提交到又拍云
				function submitToUpYun(authorization){
// 					$.ajax({
//   						 type: http_method,
//    						 url: "http://v0.api.upyun.com/pandora001/wtf.jpg",
// 						 processData:false,
// 						 contentType: false,
// 						 data:file,
// 						 headers:{
// 								"Authorization":authorization,
// 								"Content-Length": file.size,
// 								},		
//    						 success: function(responseText, textStatus, jqXHR){
// 							  alert(responseText);
// 						},
// 						error: function(XMLHttpRequest, textStatus, errorThrown){
// 							alert(textStatus);						
// 						},
// 						beforeSend:function (XMLHttpRequest) {
//     						this; // 调用本次AJAX请求时传递的options参数
// 						}
// 					});
					var xhr = new XMLHttpRequest();
						xhr.open("PUT", "http://v0.api.upyun.com/pandora001/wtf.jpg",true);
						xhr.onreadystatechange = function(){
  									if (xhr.readyState==4 && xhr.status==200){
    									alert(xhr.responseText);
   						 			}
  							};
  						xhr.setRequestHeader("Authorization",authorization);
  						xhr.setRequestHeader("Content-Length",file.size);		
						xhr.send(file);
				}
			
			 $("#restSubmit").click(function(e) {
                submitToPandora();
            });
			
			
			
			
			
			
			$("#fileInput,#file_formdata").change(function(e){  
			   		
				  file = e.target.files.item(0);
			  });
			   
			  
			  //定义存放图片对象的数组
			  var uploadImgArr = [];
			  
			 $(".addImage").change(function(e){
			  	 var files = e.target.files;
			  	 for (var i = 0,f; f = files[i]; i++) {
			  	  	 uploadImgArr.push(f);
			  	  	 var reader = new FileReader();
			  	  	 reader.onload = (function(file){
			  	  		 return function(e) {
			  	  		 var $image = $("<img/>").attr("src",this.result);
			  	  	 		var $delLink = $("<a>删除<a/>").attr("href","#");
			  	  	 		$("<li/>").append($image).append($delLink).appendTo($(".bg .cl"));
			  	  	 
            			};
			  	  	 		
			  	  	})(f);
			  	    reader.readAsDataURL(f);
			  	 }
			  });  
			   
			   
			   
			   
			 $("#formdataSubmit").click(function(e){
			    var $form =$("#imageFormData").clone();
			    var form = $form[0];
				var formData = new FormData(form);
				//var formData = new FormData();
				formData.append("file",file);
				
				var xhr = new XMLHttpRequest();
				xhr.open("POST", "<%=picBucketRequest.getRequestURL()%>",true);
				xhr.onreadystatechange = function(){
  									if (xhr.readyState==4 && xhr.status==200){
    									alert(xhr.responseText);
   						 			}
  							};
				xhr.send(formData);
				
				
				
// 				$.ajax({
//    					type: "POST",
//    					processData: false,
//   					url: "<%=picBucketRequest.getRequestURL()%>",
// 					data : formData,
// 					success : function(msg) {
// 						alert(msg);
// 					},
// 					error: function(XMLHttpRequest, textStatus, errorThrown){
// 						alert(textStatus);
// 					}
// 				});
			});

		});
</script>
</head>

<body>



	<!--提交文章业务数据-->
	<form method="post" action="article" id="articleForm">
		<table>
			<tr>
				<td><input name="layout" type="radio" size="50"
					value="horizontal" checked="checked" />文字居右</td>
				<td><input name="layout" type="radio" size="50" value="center" />文字居中</td>
			</tr>
			<tr>
				<td>作者名：</td>
				<td><input name="author" type="text" size="50" /></td>
			</tr>
			<tr>
				<td>文章标题：</td>
				<td><input name="title" type="text" size="50" /></td>
			</tr>
			<tr>
				<td>文章内容：</td>
				<td><textarea name="content" cols="55" rows="20"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="submit"
					class="hidden_submit" /></td>
			</tr>
			<tr>
				<td colspan="2"><input id="imgsToCommit" type="hidden"
					name="images" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input id="musicToCommit" type="hidden"
					name="music" />
				</td>
			</tr>
		</table>
	</form>

	<!--提交文章的图片-->
	<form method="post" action=<%=picBucketRequest.getRequestURL()%>
		enctype="multipart/form-data" id="imageForm">
		<table>
			<tr>
				<td>图片1：</td>
				<td><input type="file" name="file" multiple/></td>
			</tr>
			<tr>
				<td>图片2：</td>
				<td><input type="file" name="file" /></td>
			</tr>
		</table>
		<!-- 需要传递以下三个表单内容 -->
		<input type="hidden" name="policy"
			value=<%=picBucketRequest.getPolicy()%> /> <input type="hidden"
			name="signature" value=<%=picBucketRequest.getSignature()%> /> <input
			type="submit" value="submit" class="hidden_submit" />
	</form>

	<!--提交文章的音乐-->
	<form method="post" action=<%=musicBucketRequest.getRequestURL()%>
		enctype="multipart/form-data" id="musicForm">
		<table>
			<tr>
				<td>音乐：</td>
				<td><input name="file" type="file" size="50" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" class="hidden_submit" />
				</td>
			</tr>
		</table>
		<!-- 需要传递以下两个表单内容 -->
		<input type="hidden" name="policy"
			value=<%=musicBucketRequest.getPolicy()%> /> <input type="hidden"
			name="signature" value=<%=musicBucketRequest.getSignature()%> />
	</form>

	<table>
		<tr>
			<td><button id="submitForms">submit</button></td>
		</tr>
		<tr>
			<td><audio controls id="player"></audio>
			</td>
		</tr>
	</table>

	<h4>调用REST API上传图片</h4>
	<table>
		<tr>
			<td><input type="file" id="fileInput" /></td>
		</tr>
		<tr>
			<td>
				<button id="restSubmit">REST提交</button>
			</td>
		</tr>
	</table>


	<h4>调用FormData上传图片</h4>
	<!--提交文章的图片-->
	<form method="post" action=<%=picBucketRequest.getRequestURL()%>
		enctype="multipart/form-data" id="imageFormData">

		<!-- 需要传递以下三个表单内容 -->
		<input type="hidden" name="policy"
			value=<%=picBucketRequest.getPolicy()%> /> <input type="hidden"
			name="signature" value=<%=picBucketRequest.getSignature()%> /> <input
			type="submit" value="submit" class="hidden_submit" />
	</form>
	<table>
		<tr>
			<td>图片1：</td>
			<td><input type="file" name="file" id="file_formdata" multiple/></td>
		</tr>
		<tr>
			<td>
				<button id="formdataSubmit">FormData提交</button>
			</td>
		</tr>
	</table>
	<div class="cp">
		<dl class="bg">
			<dt>
				<input type="file" name="file" class="addImage" multiple> 
			</dt>
			<dd>
				<ul class="cl">
					<li><img src="images/u1.png" /><a href="#">删除</a>
					</li>
					<li><img src="images/u10.png" /><a href="#">删除</a>
					</li>
					<li><img src="images/u19.png" /><a href="#">删除</a>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
</body>
</html>
